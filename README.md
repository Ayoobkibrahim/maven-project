# 🚀 CI/CD Pipeline with Jenkins, Maven, and SonarQube

## 📘 Project Overview
This project demonstrates a complete **CI/CD pipeline** using **Jenkins**, **Maven**, and **SonarQube**.  
It automates **code checkout, build, testing, code analysis, quality gate validation, and notifications**.

## 🧱 Project Steps

### 1️⃣ Install Jenkins
- Install Jenkins on a **local machine** or **AWS EC2 instance**.  
- Access via browser at **http://localhost:8080**.  
- Install essential plugins:
  - Git Plugin  
  - Maven Integration Plugin  
  - SonarQube Scanner Plugin  
  - Email Extension Plugin  

### 2️⃣ Integrate SonarQube / SonarCloud
- Install **SonarQube** locally or use **SonarCloud**.  
- Connect Jenkins via **server URL** and **authentication token**.  
- Configure **SonarScanner** in Jenkins **Global Tool Configuration**.  
- Apply a **Quality Gate** to enforce code quality.

### 3️⃣ Create a Sample Maven Project
- Create a **Maven Java project** with proper folder structure.  
- Define dependencies in `pom.xml`.  
- Ensure project **builds and tests successfully**.

### 4️⃣ Create a Jenkinsfile
- Define pipeline stages:
  1. **Code Checkout**  
  2. **Build with Maven**  
  3. **Run Unit Tests**  
  4. **Code Analysis with SonarQube**  
  5. **Quality Gate Validation**  
- Place the Jenkinsfile in the **root directory**.

### 5️⃣ Store Jenkinsfile in SCM
- Commit and push Jenkinsfile to **GitHub**.  
- Configure Jenkins to pull the file from the repository during pipeline execution.

### 6️⃣ Configure Auto-Trigger / Poll SCM
- Enable **automatic builds** on code push.  
- Use **Poll SCM** or **GitHub Webhooks** for real-time triggers.  

### 7️⃣ Configure Email Notifications
- Configure **SMTP server** in Jenkins.  
- Set up email notifications for **build success, failure, or unstable builds**.  

## 🧪 Running the Pipeline
1. Clone the repository from GitHub.  
2. Open Jenkins and create a **New Pipeline Job**.  
3. Choose **Pipeline script from SCM** and provide the repository URL.  
4. Save and run the pipeline.  
5. Monitor automated execution of **build, test, and code analysis**.

## ⚙️ Tools & Technologies
| Tool | Purpose |
|------|----------|
| Jenkins | CI/CD automation server |
| Maven | Build & dependency management |
| SonarQube / SonarCloud | Static code analysis & quality gates |
| GitHub | Source code management |
| JUnit | Unit testing framework |

## 📊 Pipeline Workflow
**Code Commit → Jenkins Trigger → Build → Test → Code Analysis → Quality Gate → Email Notification**

## 🏁 Outcome
- Fully automated CI/CD pipeline triggered by code changes.  
- Continuous build and testing via Maven.  
- Code analysis with SonarQube and quality gate validation.  
- Email notifications for build results.  
- Centralized Jenkins dashboard for monitoring.

## 👨‍💻 Author
**Ayoob K Ibrahim**
