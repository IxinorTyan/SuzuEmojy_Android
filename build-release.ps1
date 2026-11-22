param(
    [switch]$NoOpen
)

$ErrorActionPreference = 'Stop'

function Find-AndroidBuildTools {
    $sdkRoots = @()
    $properties = Join-Path $PSScriptRoot 'local.properties'
    if (Test-Path -LiteralPath $properties -PathType Leaf) {
        $sdkLine = Get-Content -LiteralPath $properties | Select-String '^\s*sdk\.dir\s*[:=]\s*(.+)' | Select-Object -Last 1
        if ($sdkLine) {
            $sdkPath = [regex]::Replace($sdkLine.Matches[0].Groups[1].Value.Trim(), '\\(u[0-9a-fA-F]{4}|.)', {
                param($escape)
                $value = $escape.Groups[1].Value
                if ($value.Length -eq 5) { return [char][Convert]::ToInt32($value.Substring(1), 16) }
                return $value
            })
            $sdkRoots += $sdkPath
        }
    }
    $sdkRoots += @($env:ANDROID_HOME, $env:ANDROID_SDK_ROOT, (Join-Path $env:LOCALAPPDATA 'Android\Sdk'))
    foreach ($sdk in ($sdkRoots | Where-Object { $_ } | Select-Object -Unique)) {
        $tools = Get-ChildItem -LiteralPath (Join-Path $sdk 'build-tools') -Directory -ErrorAction SilentlyContinue |
            Where-Object { $_.Name -match '^\d+\.\d+\.\d+$' } |
            Sort-Object { [version]$_.Name } -Descending
        foreach ($tool in $tools) {
            if ((Test-Path -LiteralPath (Join-Path $tool.FullName 'aapt2.exe') -PathType Leaf) -and
                (Test-Path -LiteralPath (Join-Path $tool.FullName 'apksigner.bat') -PathType Leaf)) {
                return $tool.FullName
            }
        }
    }
    throw 'Android SDK Build-Tools (aapt2 and apksigner) not found. Check local.properties or ANDROID_HOME.'
}

function Confirm-ReleaseApk([string]$Apk, $Metadata, $Artifact) {
    $tools = Find-AndroidBuildTools
    $info = & (Join-Path $tools 'aapt2.exe') dump badging $Apk
    if ($LASTEXITCODE -ne 0) { throw 'aapt2 could not read the Release APK.' }
    $package = $info | Select-String "^package: name='([^']+)' versionCode='(\d+)' versionName='([^']*)'" | Select-Object -First 1
    if (-not $package -or ($info -contains 'application-debuggable')) {
        throw 'The APK must have release metadata and must not be debuggable.'
    }
    $fields = $package.Matches[0].Groups
    if ($fields[1].Value -cne $Metadata.applicationId -or $fields[2].Value -ne [string]$Artifact.versionCode -or
        $fields[3].Value -cne $Artifact.versionName -or $fields[1].Value -like '*.lansynctest') {
        throw 'The APK package or version does not match the intended release.'
    }
    & (Join-Path $tools 'apksigner.bat') verify $Apk
    if ($LASTEXITCODE -ne 0) { throw 'APK signature verification failed. Nothing was exported.' }
}

Push-Location -LiteralPath $PSScriptRoot

try {
    # Use the configured Java, or Android Studio's bundled JDK if Java is absent.
    if (-not $env:JAVA_HOME -and -not (Get-Command java.exe -ErrorAction SilentlyContinue)) {
        $studioJdk = Join-Path $env:ProgramFiles 'Android\Android Studio\jbr'
        if (Test-Path -LiteralPath (Join-Path $studioJdk 'bin\java.exe')) {
            $env:JAVA_HOME = $studioJdk
        }
    }

    Write-Host 'Building Release APK...' -ForegroundColor Cyan
    $buildArgs = @(':app:assembleRelease', '--console=plain')
    # AGP rejects non-ASCII Windows paths at configuration time. Apply the
    # override to this build only; do not require moving the project.
    if ($PSScriptRoot -match '[^\x00-\x7F]') {
        $buildArgs += '-Pandroid.overridePathCheck=true'
    }
    & (Join-Path $PSScriptRoot 'gradlew.bat') @buildArgs
    if ($LASTEXITCODE -ne 0) {
        throw "Gradle failed (exit code $LASTEXITCODE). No release was exported."
    }

    # Read the built APK's version rather than guessing from the source file.
    $apkDirectory = Join-Path $PSScriptRoot 'app\build\outputs\apk\release'
    $metadata = Get-Content -LiteralPath (Join-Path $apkDirectory 'output-metadata.json') -Raw | ConvertFrom-Json
    $artifacts = @($metadata.elements)
    if ($metadata.variantName -cne 'release' -or $artifacts.Count -ne 1) {
        throw 'Expected one Release APK. Check output-metadata.json.'
    }
    $artifact = $artifacts[0]
    $sourceApk = Join-Path $apkDirectory $artifact.outputFile
    $sourceMapping = Join-Path $PSScriptRoot 'app\build\outputs\mapping\release\mapping.txt'
    foreach ($source in @($sourceApk, $sourceMapping)) {
        if (-not (Test-Path -LiteralPath $source -PathType Leaf)) {
            throw "Build output missing: $source"
        }
    }
    Confirm-ReleaseApk $sourceApk $metadata $artifact

    $version = [string]$artifact.versionName -replace '[^0-9A-Za-z._+-]', '_'
    $stamp = Get-Date -Format 'yyyyMMdd-HHmmss-fff'
    $releaseRoot = Join-Path $PSScriptRoot 'releases'
    $archiveDirectory = Join-Path $releaseRoot "v$version-$($artifact.versionCode)-$stamp"
    New-Item -ItemType Directory -Path $archiveDirectory | Out-Null
    $exportedApk = Join-Path $archiveDirectory "SuzuEmojy-v$version.apk"
    Copy-Item -LiteralPath $sourceApk -Destination $exportedApk
    Copy-Item -LiteralPath $sourceMapping -Destination (Join-Path $archiveDirectory 'mapping.txt')

    # A fixed filename makes the newest successful build easy to find.
    $latestApk = Join-Path $releaseRoot 'SuzuEmojy-latest.apk'
    Copy-Item -LiteralPath $exportedApk -Destination $latestApk -Force
    Write-Host ''
    Write-Host 'Release ready.' -ForegroundColor Green
    Write-Host ('Upload to GitHub Release: {0} ({1:N2} MiB)' -f $exportedApk, ((Get-Item -LiteralPath $exportedApk).Length / 1MB))
    Write-Host "Latest APK: $latestApk"

    if (-not $NoOpen) {
        try {
            Invoke-Item -LiteralPath $archiveDirectory
        } catch {
            Write-Warning "Could not open the output folder. Open it manually: $archiveDirectory"
        }
    }
} catch {
    Write-Host "Release failed: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    Pop-Location
}
