Student Record Management System 🧑‍🎓

A simple yet effective **Student Record Management System** developed in **Java**, using **Hash Tables** for fast data storage and retrieval. This project helps manage student records such as name, ID, department, and CGPA, offering operations like add, delete, search, sort, and save.

📌 Features

- ✅ Add New Student Record
- 🗑️ Delete Existing Student by ID
- 🔍 Search Student by ID
- 🔃 Sort Students by CGPA (High to Low)
- 💾 Save Records to File
- 📂 Load Records from File
- 📊 Display All Students in a Table
- ✨ Clean and Organized UI (Java Swing)

## 🧠 Concepts Used

- Hash Tables (`HashMap`) for efficient record handling.
- File I/O for persistent data saving.
- Sorting using Java Collections.
- Object-Oriented Programming (OOP).
- Java Swing for GUI interface.

## 📸 Screenshots

| Home Page | Add Student | Search Student |
|----------|-------------|----------------|
| ![Home](screenshots/home.PNG) | ![Add](screenshots/Showall.PNG) | ![Search](screenshots/search.PNG) |

| Sort Records |
|--------------|
| ![Sort](screenshots/Sort.PNG) |

> Screenshots show a user-friendly interface to add, sort, search, and manage student records visually.

## 🛠️ Technologies Used

- Java (JDK 17+)
- Java Swing (GUI)
- HashMap (for managing records)
- FileWriter & Scanner (for saving/loading)
- Eclipse IDE (Recommended)

## 🗂️ Project Structure
StudentRecordSystem/
├── Main.java
├── Student.java
├── StudentManager.java
├── GUI.java
├── records.txt
└── screenshots/
├── home.PNG
├── Showall.PNG
├── search.PNG
└── Sort.PNG

## 🚀 How to Run

1. Clone the repository:
   bash
   git clone https://github.com/Rahim-Ahmed15/Student-Record-Management-System.git
Open the project in your Java IDE (e.g., Eclipse or IntelliJ).

Run Main.java.

Use the GUI to add, delete, sort, or search students.

Make sure you have Java 17+ installed.
📁 File Format
Records are stored in the following format inside records.txt:

objectivec
Copy
Edit
ID,Name,Department,CGPA
💡 Future Improvements
Add authentication (admin login).

Export to Excel/PDF.

Add student photos.

Add filters (by department, CGPA range).

Integrate database (e.g., MySQL) for large-scale usage.

🤝 Contribution
Pull requests are welcome! Feel free to fork this repository and improve it.

📄 License
This project is open-source and free to use under the MIT License.
