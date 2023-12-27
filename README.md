# ISOCrypter Ransomware

ISOCrypter is a simple encryption application / ransomware written in Java for educational purposes. It allows users to encrypt and decrypt files using AES encryption.

> **DISCLAIMER:**
>
> This software, ISOCrypter, is intended for educational purposes only. The author is not responsible for any misuse or malicious use of this program. It is the user's responsibility to ensure that the program is
> used ethically and in compliance with applicable laws and regulations. The author disclaims any liability for any damage or loss incurred through the misuse of this software. By using ISOCrypter, you agree that 
> the author is under no obligation for any consequences resulting from its use for malicious purposes.
> 
> Please use this software responsibly and only in accordance with ethical standards.


## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## Overview
ISOCrypter is designed to provide a basic understanding of file encryption and decryption processes (can be used as a ransomware) using Java. It employs AES encryption to secure files and **MUST** be used for educational purposes only. **DO NOT** use this application for malicious purposes and be very careful when running this on your local machine.

## Features
- Runs both on Windows and Unix environments
- Uses multi-thread approach for faster processing
- Encrypts files in the specified path
- Decrypts encrypted files with a provided key
- Excludes system folders based on the operating system

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Apache Commons CLI library (included in the project)
- Apache Maven (for building and managing the project)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/ISO53/ISOCrypter-Ransomware.git
   ```
   
2. Locate the Project Directory:
	```bash
   	cd isocrypter_ransomware
   	```
 
3. Build the Project Using Maven:
   ```bash
   mvn clean package
   ```

# Usage
Run the application with the following command:
```bash
java -jar target/isocrypter_ransomware-jar-with-dependencies.jar [options]
```

> BE VERY CAREFUL WHEN RUNNING THIS PROGRAM ON YOUR LOCAL MACHINE. PROGRAM CURRENTLY PRINTS THE ENCRYPTION KEY TO THE TERMINAL, YOU CAN USE THAT FOR DECRYPTION. DO NOT, UNDER AND CIRCUMSTANCES USE THIS PROGRAM FOR MALICIOUS PURPOSES.

## Options
* -h,&emsp;--help:&emsp;&emsp;&emsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Prints help message.
* -p,&emsp;--path [path]:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Specify the path for the program to run. The default is the root path of the OS.
* -e,&emsp;--encrypt:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Encrypts all the files in the specified path.
* -d,&emsp;--decrypt [key]:&nbsp;&nbsp;&nbsp;&nbsp;Decrypts all the files in the specified path with the provided key.

# Contributing
Contributions are welcome! Feel free to open issues or pull requests.

## License

 This project is licensed under the [GNU General Public License v3.0](LICENSE).

 [![Follow me on GitHub](https://img.shields.io/github/followers/iso53?label=Follow%20%40iso53&style=social)](https://github.com/iso53)
