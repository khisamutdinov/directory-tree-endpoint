# Directory Tree Application

A command-line application for managing a virtual directory structure with create, delete, move, and list operations.

## Features

- Create directories and nested directories
- List the entire directory structure in a tree format
- Move directories from one location to another
- Delete directories
- Support for multi-line command input with continuation character (`\`)

## Requirements

- Java 21 or higher
- Maven 3.8+ (or use the included Maven wrapper)
- Docker (optional, for containerized usage)

## Building the Application

### Using Maven

You can build the application using the included Maven wrapper:

```bash
# On Unix-based systems
./mvnw clean package

# On Windows
mvnw.cmd clean package
```

Or if you have Maven installed:

```bash
mvn clean package
```

This will create a runnable JAR file in the `target` directory named `TreeDirectory-1.0.jar`.

### Using Docker

To build a Docker image:

```bash
docker build -t directory-tree-app .
```

## Running the Application

### Using Java

After building, you can run the application directly with Java:

```bash
java -jar target/TreeDirectory-1.0.jar
```

### Using Docker

If you've built the Docker image, you can run it in interactive mode:

```bash
docker run -it directory-tree-app
```

## Command Syntax

The application supports the following commands:

- `CREATE path` - Creates a directory at the specified path
- `LIST` - Lists all directories in a tree structure
- `MOVE source destination` - Moves a directory from source to destination
- `DELETE path` - Deletes the directory at the specified path
- `EXIT` - Quits the application

### Path Format

Paths should use forward slashes (`/`) to separate directory names, e.g., `fruits/apples/fuji`.

### Multi-line Input

You can enter multiple commands at once, with each command on a new line. You can also use a backslash (`\`) at the end
of a line to continue the command on the next line.

## Example Usage

```
CREATE fruits
CREATE vegetables
CREATE grains
CREATE fruits/apples
CREATE fruits/apples/fuji
LIST
CREATE grains/squash
MOVE grains/squash vegetables
CREATE foods
MOVE grains foods
MOVE fruits foods
MOVE vegetables foods
LIST
```

Another example with formatted input:

```
CREATE birds/hummingbird \
CREATE birds/hummingbird/anna \
CREATE animals \
CREATE animals/mammals \
CREATE animals/mammals/penguin \
MOVE animals/mammals/penguin birds \
LIST
```

## Development

This project uses:

- Java 21
- Maven for dependency management and building
- Lombok for reducing boilerplate code
- JUnit 5 for testing

### Project Structure

- `model` - Contains the core data structures (Directory, FileSystem)
- `command` - Command pattern implementation for different operations
- `utils` - Utility classes for path validation, directory listing, etc.
