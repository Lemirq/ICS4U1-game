from PIL import Image
import os

# # Directory containing the images
# image_directory = './sprites/player_running/'

# # Get a list of all image files in the directory
# image_files = [f for f in os.listdir(image_directory) if f.endswith('.png')]

# # Loop through each image, open it, resize it, and save it
# for image_file in image_files:
#     image_path = os.path.join(image_directory, image_file)
    
#     # Open the image
#     with Image.open(image_path) as img:
#         # Resize the image to 50x50
#         img_resized = img.resize((200, 200))
        
#         # Save the resized image (overwriting the original)
#         img_resized.save(image_path)

# print("All images have been resized.")

with Image.open("./sprites/idle.png") as img:
    img_resized = img.resize((50, 50))
    img_resized.save("./sprites/idle.png")
