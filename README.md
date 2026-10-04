# AnalyzeTextFromGZ

This project is designed to efficiently process large data files using Java 21 and Gradle with Groovy. It leverages multithreading and asynchronous processing to handle large inputs and produces output files based on the processed data.

## Metrics
Avarege execution time of GZ comressed txt: 5462 ms

Avarege execution time of uncomressed txt: 4923 ms

## How to Run

1. **Build the project** (JDK 21+):
   ```bash
   ./gradlew shadowJar
   ```
2. **Prepare your input files**: put a GZ-compressed or plain text file into `src/main/resources/input/`. The input data (`lng-4.txt`, about 80 MB) is not stored in the repository. Lines have the form `"value";"value";"value"`.
3. **Run**:
   ```bash
   java -Xmx1G -jar build/libs/PeacockTeamTestTask-1.0-SNAPSHOT-all.jar lng-4.txt.gz
   # or
   java -Xmx1G -jar build/libs/PeacockTeamTestTask-1.0-SNAPSHOT-all.jar lng-4.txt
   ```
   The result is written to `src/main/resources/output/output.txt` (the directory is created automatically).

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
