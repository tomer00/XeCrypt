# XeCrypt - Secure File Encryption and Decryption

![Logo](https://github.com/tomer00/TaskTick/assets/68748487/521099ec-e7be-4040-9dfa-86e019d5a800)

XeCrypt is a versatile Java Swing application that empowers you to encrypt and decrypt various file types, including images, videos, and documents, providing you with a secure and user-friendly solution for data protection. With XeCrypt, you can ensure the confidentiality and integrity of your sensitive files.

![Idea](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
[![License Badge](https://img.shields.io/badge/license-MIT-blue?style=for-the-badge)](LICENSE)
![Java](https://img.shields.io/badge/Java%20Swing-000000.svg?style=for-the-badge&logo=data:image/svg%2bxml;base64,PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPCEtLSBVcGxvYWRlZCB0bzogU1ZHIFJlcG8sIHd3dy5zdmdyZXBvLmNvbSwgR2VuZXJhdG9yOiBTVkcgUmVwbyBNaXhlciBUb29scyAtLT4KPHN2ZyB3aWR0aD0iODAwcHgiIGhlaWdodD0iODAwcHgiIGZpbGw9IiMwMDAwMDAiIHZlcnNpb249IjEuMSIgdmlld0JveD0iMCAwIDMyIDMyIiB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciPgogPHBhdGggZD0ibTE3LjYyNSAzYzEuNDAyMyAzLjMwODYtNS4wMjczIDUuMzM1OS01LjYyNSA4LjA5MzgtMC41NDY4OCAyLjUzMTIgMy44MDg2IDUuNSAzLjgxMjUgNS41LTAuNjY0MDYtMS4wNDY5LTEuMTQ4NC0xLjkyOTctMS44MTI1LTMuNTYyNS0xLjEyNS0yLjc1NzggNi44NTU1LTUuMjQ2MSAzLjYyNS0xMC4wMzF6bTQuMjUgNC41OTM4cy01LjYyMTEgMC4zNTU0Ny01LjkwNjIgNC4wMzEyYy0wLjEyODkxIDEuNjM2NyAxLjQ4NDQgMi40OTYxIDEuNTMxMiAzLjY4NzUgMC4wMzkwNjMgMC45NzI2Ni0wLjk2ODc1IDEuNzgxMi0wLjk2ODc1IDEuNzgxMnMxLjgwODYtMC4zMjgxMiAyLjM3NS0xLjgxMjVjMC42MjUtMS42NDg0LTEuMjE4OC0yLjc3MzQtMS4wMzEyLTQuMDkzOCAwLjE3OTY5LTEuMjYxNyA0LTMuNTkzOCA0LTMuNTkzOHptMS4zNzUgOC40Njg4Yy0wLjU4OTg0LTAuMDI3MzQ0LTEuMjUzOSAwLjE5MTQxLTEuODQzOCAwLjYyNSAxLjE2NDEtMC4yNTc4MSAyLjE1NjIgMC40NzI2NiAyLjE1NjIgMS4zMTI1IDAgMS44ODI4LTIuNjg3NSAzLjY1NjItMi42ODc1IDMuNjU2MnM0LjE1NjItMC40NjQ4NCA0LjE1NjItMy41NjI1YzAtMS4yNzczLTAuODAwNzgtMS45ODQ0LTEuNzgxMi0yLjAzMTJ6bS0xMS4wMzEgMC4wMzEyNWMtMS40NDkyIDAuMDUwNzgxLTQuMzQzOCAwLjI4OTA2LTQuMzQzOCAxLjQwNjIgMCAxLjU1NDcgNi43NDIyIDEuNjc1OCAxMS41NjIgMC43MTg3NSAwIDAgMS4zMTI1LTAuOTE0MDYgMS42NTYyLTEuMjUtMy4xNjAyIDAuNjU2MjUtMTAuMzc1IDAuNzU3ODEtMTAuMzc1IDAuMTg3NSAwLTAuNTIzNDQgMi4zMTI1LTEuMDYyNSAyLjMxMjUtMS4wNjI1cy0wLjMyODEyLTAuMDE1NjI1LTAuODEyNSAwem0tMC40Mzc1IDIuODc1Yy0wLjc5Mjk3IDAtMS45Njg4IDAuNjE3MTktMS45Njg4IDEuMjE4OCAwIDEuMjEwOSA1Ljk2ODggMi4xNDA2IDEwLjM3NSAwLjM3NWwtMS41MzEyLTAuOTM3NWMtMi45ODgzIDAuOTc2NTYtOC41MDc4IDAuNjUyMzQtNi44NzUtMC42NTYyNXptMC43NSAyLjcxODhjLTEuMDgyIDAtMS43ODEyIDAuNjgzNTktMS43ODEyIDEuMTg3NSAwIDEuNTUwOCA2LjQ2NDggMS43MDMxIDkuMDMxMiAwLjEyNWwtMS42MjUtMS4wNjI1Yy0xLjkxNDEgMC44MjQyMi02LjczMDUgMC45NDUzMS01LjYyNS0wLjI1em0tMy42MjUgMS40MDYyYy0xLjc2NTYtMC4wMzUxNTYtMi45MDYyIDAuNzY1NjItMi45MDYyIDEuNDM3NSAwIDMuNTc0MiAxOC4wOTQgMy40MDIzIDE4LjA5NC0wLjI1IDAtMC42MDU0Ny0wLjcxNDg0LTAuODk0NTMtMC45Njg3NS0xLjAzMTIgMS40NzY2IDMuNDkyMi0xNC43ODEgMy4yMTg4LTE0Ljc4MSAxLjE1NjIgMC0wLjQ2ODc1IDEuMjAzMS0wLjkzNzUgMi4zMTI1LTAuNzE4NzVsLTAuOTM3NS0wLjUzMTI1Yy0wLjI3NzM0LTAuMDQyOTY5LTAuNTU4NTktMC4wNTg1OTQtMC44MTI1LTAuMDYyNXptMTcuMDk0IDIuNDA2MmMtMi43NSAyLjY2MDItOS43MTA5IDMuNjEzMy0xNi43MTkgMS45Njg4IDcuMDA3OCAyLjkyOTcgMTYuNjg0IDEuMzAwOCAxNi43MTktMS45Njg4eiIgZmlsbD0iI2ZmZiIvPgo8L3N2Zz4K)

## Table of Contents 📄

- [Screenshots](#screenshots-)
- [Features](#features-)
- [Getting Started](#getting-started-)
  - [Installation](#installation)
- [Usage](#usage-)
- [Security](#security-)
- [License](#license-)

---

## ScreenShots 🫣

<div>
  <img align="right" height="240" src="https://github.com/tomer00/XeCrypt/assets/68748487/1a919d0f-c405-4651-bd96-7be305f79dd2" alt="LockView"/>
  <img align="right" width="16px" height="240px" src="https://github.com/hindu744/readme_test/assets/112962567/a8ffee98-73bf-49d9-90bb-6c16f420ce20"/>

- To enhance security, you can access the Quick View feature by entering a PIN.
- Drop any type of file in the designated area, and it will be automatically encrypted and sorted into the appropriate folder based on its file type.
- Images can be viewed directly by clicking on a card in a custom-written image viewer, where the image is scaled to fit the available area.
- To restore files, simply click on the "Decrypt" button. After decryption, file manager will be opened, where you will find your original file successfully restored.
- To Delete a file, simply click on the "Cross" button.


</div>
   <img height=60px/>
  <img width="100%" src="https://github.com/tomer00/XeCrypt/assets/68748487/b0612ae2-66c9-43be-95e3-064a806be6ee" alt="EncFiles"/>
  <img width="100%" src="https://github.com/tomer00/XeCrypt/assets/68748487/99a5cfc8-810d-42d5-9390-997edc2061b1" alt="DecFiles"/>

<img height=60px/>

---

## Features 📱

- **File Encryption**: Safeguard your files with robust encryption algorithms, rendering them inaccessible without the correct decryption key.

- **Versatile Compatibility**: XeCrypt supports encryption and decryption of various file formats, including images, videos, documents, and more.

- **User-Friendly Interface**: The Java Swing-based user interface offers an intuitive and visually appealing experience for easy file management.

- **Export Encrypted Files**: Safely share your encrypted files with others, who can then use XeCrypt to decrypt them.

- **Cross-Platform Compatibility**: XeCrypt is designed to run on various platforms, making it accessible to a broad user base.

## Getting Started 🚀
#### Make sure Java 17+ 🍵 is installed on system

### Installation

1. Clone this repository or download the latest release from the [Releases](https://github.com/tomer00/XeCrypt/releases) page.
2. Extract the downloaded zip file and enter the `XeCrypt` folder.
3. Run `XeCrypt.jar` with Java:

```bash
java -jar XeCrypt.jar
```

## Usage 💻

1. Launch XeCrypt with the above command, or you can a shortcut for so.
2. Enter your pin to unlock or create a new pin if opened initially.
3. Drag and drop your files to encrypt them.
4. You can preview images by clicking on them.
5. Decryption or deletion can be done using respected buttons.

## Security 🔐

XeCrypt employs industry-standard encryption techniques to ensure the confidentiality and integrity of your files. It's essential to keep your encryption keys and passwords secure.

## Contribution 👋

Contributions to the XeCrypt project are welcome! If you'd like to contribute, please follow these steps:

1. Fork the repository and clone it to your local machine.
2. Create a new branch for your feature or bug fix.
3. Implement your changes and thoroughly test them.
4. Create a pull request, detailing the changes you've made.

## License 📃

This project is licensed under the [MIT License](LICENSE).

---
<div align="center">Developed with ❤️ by <a href="https://linkedin.com/in/tomer00" target="_blank">Himanshu Tomer</a></div>
