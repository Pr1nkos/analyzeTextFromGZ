# PeacockTeamTestTask

This project is designed to efficiently process large data files using Java 21 and Gradle with Groovy. It leverages multithreading and asynchronous processing to handle large inputs and produces output files based on the processed data.

## How to Run

1. **Prepare your input files**:
   - Place your GZ compressed text files in the `src/main/resources/input` directory.

2. **Execute the program**:
   - Use the following command to run the program:
     ```bash
     java -Xmx1G -jar PeacockTeamTestTask-1.0-SNAPSHOT-all.jar lng.txt.gz
     ```
   - This command processes the `lng.txt.gz` file and produces the output in the `src/main/resources/output` directory.

## Project Structure

- **Input Directory**: `src/main/resources/input`
  - This is where you place your GZ compressed input files.

- **Output Directory**: `src/main/resources/output`
  - This is where the program will generate the processed output files.

## Key Features

- **Java 21**: The project is built using the latest features and capabilities of Java 21.
- **Gradle with Groovy**: The build system is managed by Gradle, utilizing Groovy as the scripting language for build configuration.
- **Multithreading and Asynchronous Processing**: The program is designed to handle large datasets concurrently, making use of multithreading to optimize performance.
- **Object-Oriented Principles**: The codebase follows OOP principles
- **Design Patterns**: Strategy

## Design and Architecture

- **Concurrent File and Line Processing**: The program uses a combination of concurrent file processing and line processing to handle large files efficiently. The `ConcurrentGraphLineProcessor` class processes each line in a separate thread, while the `ConcurrentFileProcessor` manages the overall file processing.
  
- **Input/Output Management**: The project organizes input and output files systematically, ensuring that input files are processed from the `src/main/resources/input` directory and the results are stored in `src/main/resources/output`.

- **Executor Service Management**: The program uses an `ExecutorService` to manage thread pools, ensuring that resources are efficiently utilized and that the program can handle large data sets without performance degradation.

## Installation

1. **Clone the repository**:
   ```bash
   git clone https://github.com/Pr1nkos/PeacockTeamTestTask.git
   ```
2. **Build the project:**
   ```bash
   ./gradlew build
   ```
4. **Run the project using the instructions provided above.**
