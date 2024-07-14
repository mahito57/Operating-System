#!/bin/bash

directory="$1"
#directory=/home/codac/shell_scripting/Shell-Scripting-Assignment-Files/Workspace/submissions
targetpath="$2"

mkdir -p "$targetpath"

# Create the primarytarget directory within the destination directory
primary_target_dir="$targetpath/primarytarget"
mkdir -p "$primary_target_dir"

# Navigate to the specified directory
cd "$directory"
# Loop through all zip files in the directory
for zip_file in *.zip; do
#  echo "Extracting $zip_file..."
  temp=${zip_file: -11: -4}
  mkdir "$primary_target_dir/"$temp""
  target="$primary_target_dir/"$temp""
  unzip "$zip_file" -d "$target"
done

#Copying .c files
# Specify the source directory
source_directory="$primary_target_dir"

# Specify the destination directory
mkdir "$targetpath/c"
destination_directory="$targetpath/c"

# Specify the pattern for the parent folder name
pattern="18"

# Get the root directory
root_directory="/"

# Traverse through all folders and subdirectories
find "$source_directory" -type d -print0 | while IFS= read -r -d '' folder; do
  # Iterate over each .c file in the current folder
  find "$folder" -type f -name "*.c" -print0 | while IFS= read -r -d '' file; do
    # Get the parent folder path
    parent_folder=$(dirname "$file")
    
    # Traverse back to the root directory
    while [[ "$parent_folder" != "$root_directory" ]]; do
      # Get the parent folder name
      parent_folder_name=$(basename "$parent_folder")
      
      # Check if the parent folder name matches the pattern
      if [[ "$parent_folder_name" == *"$pattern"* ]]; then
        # Create the destination folder with the parent folder name
        destination_folder="$destination_directory/$parent_folder_name"
        mkdir -p "$destination_folder"

        # Copy the .c file to the destination folder
        cp "$file" "$destination_folder/main.c"
        break
      fi

      # Move up to the next parent directory
      parent_folder=$(dirname "$parent_folder")
    done
  done
done

#Copying .java files
# Specify the source directory
source_directory="$primary_target_dir"

# Specify the destination directory
mkdir "$targetpath/java"
destination_directory="$targetpath/java"

# Specify the pattern for the parent folder name
pattern="18"

# Get the root directory
root_directory="/"

# Traverse through all folders and subdirectories
find "$source_directory" -type d -print0 | while IFS= read -r -d '' folder; do
  # Iterate over each .c file in the current folder
  find "$folder" -type f -name "*.java" -print0 | while IFS= read -r -d '' file; do
    # Get the parent folder path
    parent_folder=$(dirname "$file")
    
    # Traverse back to the root directory
    while [[ "$parent_folder" != "$root_directory" ]]; do
      # Get the parent folder name
      parent_folder_name=$(basename "$parent_folder")
      
      # Check if the parent folder name matches the pattern
      if [[ "$parent_folder_name" == *"$pattern"* ]]; then
        # Create the destination folder with the parent folder name
        destination_folder="$destination_directory/$parent_folder_name"
        mkdir -p "$destination_folder"

        # Copy the .c file to the destination folder
        cp "$file" "$destination_folder/Main.java"
        break
      fi

      # Move up to the next parent directory
      parent_folder=$(dirname "$parent_folder")
    done
  done
done

#copying .py files
# Specify the source directory
source_directory="$primary_target_dir"

# Specify the destination directory
mkdir "$targetpath/python"
destination_directory="$targetpath/python"

# Specify the pattern for the parent folder name
pattern="18"

# Get the root directory
root_directory="/"

# Traverse through all folders and subdirectories
find "$source_directory" -type d -print0 | while IFS= read -r -d '' folder; do
  # Iterate over each .c file in the current folder
  find "$folder" -type f -name "*.py" -print0 | while IFS= read -r -d '' file; do
    # Get the parent folder path
    parent_folder=$(dirname "$file")
    
    # Traverse back to the root directory
    while [[ "$parent_folder" != "$root_directory" ]]; do
      # Get the parent folder name
      parent_folder_name=$(basename "$parent_folder")
      
      # Check if the parent folder name matches the pattern
      if [[ "$parent_folder_name" == *"$pattern"* ]]; then
        # Create the destination folder with the parent folder name
        destination_folder="$destination_directory/$parent_folder_name"
        mkdir -p "$destination_folder"

        # Copy the .c file to the destination folder
        cp "$file" "$destination_folder/main.py"
        break
      fi

      # Move up to the next parent directory
      parent_folder=$(dirname "$parent_folder")
    done
  done
done





