# AnalyzeTextFromGZ

This project is designed to efficiently process large data files using Java 21 and Gradle with Groovy. It leverages multithreading and asynchronous processing to handle large inputs and produces output files based on the processed data.

## Metrics
Avarege execution time of GZ comressed txt: 5462 ms

Avarege execution time of uncomressed txt: 4923 ms

## How to Run

1. **Prepare your input files**:
   - Place your GZ compressed text files in the `src/main/resources/input` directory.
   - OR place your text files in the `src/main/resources/input` directory.

2. **Execute the program**:
   - Use the following command to run the program:
     ```bash
     java -Xmx1G -jar analyzeTextFromGZ.jar lng.txt.gz
     OR
     java -Xmx1G -jar analyzeTextFromGZ.jar lng.txt
     ```
   - This command processes the `lng.txt.gz` or `lng.txt` file and produces the output in the `src/main/resources/output` directory.

## Project Structure

- **Input Directory**: `src/main/resources/input`
  - This is where you place your GZ compressed or txt input files.

- **Output Directory**: `src/main/resources/output`
  - This is where the program will generate the processed output files.

## Key Features

- **Java 21**: The project is built using the latest features and capabilities of Java 21.
- **Gradle with Groovy**: The build system is managed by Gradle, utilizing Groovy as the scripting language for build configuration.
- **Object-Oriented Principles**: The codebase follows OOP principles, many abstractions btw
- **Design Patterns**: Strategy

## Design and Architecture
  
- **Input/Output Management**: The project organizes input and output files systematically, ensuring that input files are processed from the `src/main/resources/input` directory and the results are stored in `src/main/resources/output`.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Pr1nkos/analyzeTextFromGZ.git
   ```
2. **Build the project:**
   ```bash
   ./gradlew build
   ```
4. **Run the project using the instructions provided above.**
