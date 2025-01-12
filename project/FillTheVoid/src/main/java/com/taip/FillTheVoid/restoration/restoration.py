import cv2
import sys
import json

if __name__ == '__main__':

    selected_model = sys.argv[1]
    json_corners = sys.argv[2]

    print(selected_model)
    print(json_corners)


    corners_data = json.loads(json_corners)

    # Extract the list of corners from the "corners" field
    corners_list = corners_data.get("corners")

    if not corners_list:
        print("The list of corners is empty or missing!")
        sys.exit(1)

    # Extract the first square (first item in the list)
    first_square = corners_list[0]

    left_up_x = first_square["leftUp"]["x"]
    left_up_y = first_square["leftUp"]["y"]

    right_up_x = first_square["rightUp"]["x"]
    right_up_y = first_square["rightUp"]["y"]

    left_down_x = first_square["leftDown"]["x"]
    left_down_y = first_square["leftDown"]["y"]

    right_down_x = first_square["rightDown"]["x"]
    right_down_y = first_square["rightDown"]["y"]


    # left_up_x = corners["leftUp"]["x"]
    # left_up_y = corners["leftUp"]["y"]
    #
    # # Accesăm coordonatele 'RightUp'
    # right_up_x = corners["rightUp"]["x"]
    # right_up_y = corners["rightUp"]["y"]
    #
    # # Accesăm coordonatele 'LeftDown'
    # left_down_x = corners["leftDown"]["x"]
    # left_down_y = corners["leftDown"]["y"]
    #
    # # Accesăm coordonatele 'RightDown'
    # right_down_x = corners["rightDown"]["x"]
    # right_down_y = corners["rightDown"]["y"]

    # Afișăm coordonatele
#     print("LeftUp x:", left_up_x, ", y:", left_up_y)
#     print("RightUp x:", right_up_x, ", y:", right_up_y)
#     print("LeftDown x:", left_down_x, ", y:", left_down_y)
#     print("RightDown x:", right_down_x, ", y:", right_down_y)


    file_path = "src/main/java/com/taip/FillTheVoid/restoration/restored_image.png"

    image = cv2.imread(file_path)
    image_gray = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)

    height, width, channels = image.shape
    print(f"Image Dimensions: {height}x{width}, Channels: {channels}")

    # Display the image using OpenCV
#     cv2.imshow("Restored Image", image)

    result = cv2.imwrite(file_path, image_gray)


    # Wait until a key is pressed and then close the window
#     cv2.waitKey(0)
#     cv2.destroyAllWindows()

    print("a")

