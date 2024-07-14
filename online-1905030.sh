#!/bin/bash

directory="$1"

targetpath="$2"

mkdir -p "$targetpath"

# Navigate to the specified directory
cd "$directory"

# Traverse through all folders and subdirectories
find "$directory" -type d -print0 | while IFS= read -r -d '' folder; do
  # Iterate over each .c file in the current folder
  find "$folder" -type f -name "*.txt" -print0 | while IFS= read -r -d '' file; do
    cp "$file" "$targetpath"
   done
done
