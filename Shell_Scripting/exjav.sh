#!/bin/bash

# Specify the root directory
root_directory="$1/java"

# Specify the test case directory
test_case_directory="$2"

# Specify the answer directory
answer_directory="$3"

# Specify the output CSV file
output_csv="comparison_results.csv"

# Counter for test case files
counter=1

# Find and compile .java files in the root directory and subdirectories
find "$root_directory" -type f -name "*.java" -print0 | while IFS= read -r -d '' file; do
  # Get the parent directory of the .java file
  parent_directory=$(dirname "$file")

  # Change to the parent directory
  cd "$parent_directory"

  # Compile the .java file
  echo "$file"
  javac "$file"
  
    find "$test_case_directory" -type f -name "*.txt" | while IFS= read -r test_case; do
    # Execute the compiled file with the test case
    java Main.java < "$test_case" > "out${counter}.txt"
    
     # Compare the output file with the corresponding answer file
    diff_result=$(diff -q "out${counter}.txt" "${answer_directory}/ans${counter}.txt")
    
    # Check if the files are identical or different
    if [ -z "$diff_result" ]; then
      # Output matches to CSV file
      echo "${counter},Match" >> "$output_csv"
    else
      # Output non-match to CSV file
      echo "${counter},No Match" >> "$output_csv"
    fi

    # Increment the counter for the next output file
    ((counter++))
  done
  
  # Go back to the root directory
  cd "$root_directory"
done
