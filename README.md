# 🚀 Maven CI/CD Project with Jenkins, SonarQube, and Docker

## 📘 Project Overview
This project demonstrates a **complete CI/CD pipeline** for a **Java Maven project** using **Jenkins**, **SonarQube**, and **Docker**.  

The pipeline automates the entire software delivery process, including:  
- **Code checkout** from GitHub  
- **Build** and **unit testing** using Maven  
- **Code analysis** with SonarQube and enforcement of **Quality Gate**  
- **Docker image creation**, tagging, and push to DockerHub  
- **Notifications** on pipeline status  

This ensures that every code change is **tested, analyzed, and deployed consistently**.

---

## 🧱 Project Steps

### 1️⃣ Create a Maven Project
- Initialize a **Java Maven project** with standard folder structure.  
- Add necessary **dependencies** for unit testing.  
- Ensure the project **builds successfully** locally and unit tests pass.

---

### 2️⃣ Setup Jenkins Pipeline
- Install Jenkins on a **local machine** or **cloud server**.  
- Install required plugins:
  - Git Plugin  
  - Maven Integration Plugin  
  - SonarQube Scanner Plugin  
  - Docker Pipeline Plugin  
  - Email Extension Plugin  
- Create a **pipeline job** in Jenkins linked to your GitHub repository.  
- Configure **automatic triggers** via GitHub webhooks or polling SCM.

---

### 3️⃣ Integrate SonarQube
- Install **SonarQube** locally or use **SonarCloud**.  
- Connect Jenkins to SonarQube using **server URL** and **authentication token**.  
- Define a **Quality Gate** to enforce code quality standards.  
- The pipeline should **fail automatically** if the Quality Gate is not passed.

---

### 4️⃣ Implement CI/CD Stages
The Jenkins pipeline should include the following stages:

1. **Code Checkout:** Pull the latest code from GitHub.  
2. **Build:** Compile the Java project using Maven.  
3. **Unit Test:** Run automated unit tests to ensure correctness.  
4. **Code Analysis:** Analyze the code with SonarQube.  
5. **Quality Gate:** Stop the pipeline if code quality standards fail.  
6. **Docker Build & Push:**  
   - Log in to DockerHub  
   - Build a Docker image from the Maven project  
   - Tag the image with the **current build number**  
   - Push the image to DockerHub  
   - Log out from DockerHub  

---

### 5️⃣ Docker Integration
- Write a **Dockerfile** to containerize your Maven application.  
- Use **multi-stage builds**: first compile the project, then run it in a minimal runtime image.  
- Test Docker image locally before pushing.

---

### 6️⃣ Pipeline Benefits
- Fully **automated CI/CD** for Maven projects.  
- **Early detection** of build errors or failing tests.  
- Enforced **code quality** with SonarQube.  
- Dockerized application ready for deployment in **any environment**.  
- Centralized monitoring via **Jenkins dashboard**.

---

### 7️⃣ Recommended Tools & Technologies
| Tool | Purpose |
|------|----------|
| Jenkins | CI/CD automation server |
| Maven | Build & dependency management |
| SonarQube / SonarCloud | Static code analysis & quality gates |
| Docker | Containerization and deployment |
| GitHub | Source code version control |
| JUnit | Unit testing framework |

---

### 8️⃣ Workflow Summary
**Code Commit → Jenkins Trigger → Build → Unit Test → SonarQube Analysis → Quality Gate → Docker Build & Push → Notifications**

---

## 👨‍💻 Author
**Ayoob K Ibrahim**
