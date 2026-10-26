param(
    [switch]$NoOpen
)

$ErrorActionPreference = 'Stop'
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
    & (Join-Path $PSScriptRoot 'gradlew.bat') :app:assembleRelease --console=plain
    if ($LASTEXITCODE -ne 0) {
        throw "Gradle failed (exit code $LASTEXITCODE). No release was exported."
    }

    # Read the built APK's version rather than guessing from the source file.
    $apkDirectory = Join-Path $PSScriptRoot 'app\build\outputs\apk\release'
    $metadata = Get-Content -LiteralPath (Join-Path $apkDirectory 'output-metadata.json') -Raw | ConvertFrom-Json
    $artifacts = @($metadata.elements)
    if ($artifacts.Count -ne 1) {
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
    Write-Host "Latest APK: $latestApk"
    Write-Host "Archive:    $archiveDirectory"

    if (-not $NoOpen) {
        try {
            Invoke-Item -LiteralPath $releaseRoot
        } catch {
            Write-Warning "Could not open the output folder. Open it manually: $releaseRoot"
        }
    }
} catch {
    Write-Host "Release failed: $($_.Exception.Message)" -ForegroundColor Red
    exit 1
} finally {
    Pop-Location
}
