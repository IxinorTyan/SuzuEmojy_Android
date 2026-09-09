import os
from PIL import Image

src_ico = "ico.png"
if not os.path.exists(src_ico):
    print("Error: ico.png not found in root!")
    exit(1)

img = Image.open(src_ico)

# Standard Android Mipmap dimensions
densities = {
    "mipmap-mdpi": (48, 48),
    "mipmap-hdpi": (72, 72),
    "mipmap-xhdpi": (96, 96),
    "mipmap-xxhdpi": (144, 144),
    "mipmap-xxxhdpi": (192, 192)
}

res_dir = "app/src/main/res"

for folder, size in densities.items():
    target_folder = os.path.join(res_dir, folder)
    os.makedirs(target_folder, exist_ok=True)
    
    # Resize and save ic_launcher.png
    resized_img = img.resize(size, Image.Resampling.LANCZOS)
    
    launcher_path = os.path.join(target_folder, "ic_launcher.png")
    round_path = os.path.join(target_folder, "ic_launcher_round.png")
    
    resized_img.save(launcher_path, "PNG")
    resized_img.save(round_path, "PNG")
    print(f"Generated {size[0]}x{size[1]} icon for {folder}")

print("All launcher icons successfully replaced with ico.png!")
