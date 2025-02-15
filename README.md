# JenkinsQA_2024_fall

<h1 align="center">Project for Testing the CI/CD Tool Jenkins</h1>
<p align="center">
  <img src="media/jenkins.svg" alt="Jenkins logo" width="100"/>
</p>

> Jenkins is an automation tool designed for Continuous Integration (CI) and Continuous Deployment (CD). It allows automatic execution of build, testing, and deployment processes whenever code changes are made in the repository.

##  Content:
- <a href="#cases"> Test documentation</a>
- <a href="#settings"> Settings</a>
- <a href="#autotests"> Running Tests Locally</a>
- <a href="#jenkins"> Сборка в Jenkins</a>
- <a href="#allureReport"> Пример Allure-отчета</a>
- <a href="#tg"> Уведомления в Telegram с использованием бота</a>

## Tech Stack
<p align="center">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white"/>
  <img src="https://img.shields.io/badge/TestNG-FF8C00?style=for-the-badge&logo=testng&logoColor=white"/>
  <img src="https://img.shields.io/badge/Selenium-43B02A?style=for-the-badge&logo=selenium&logoColor=white"/>
  <img src="https://img.shields.io/badge/RestAssured-6DB33F?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/WireMock-FF4154?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/HTTP-005571?style=for-the-badge&logo=http&logoColor=white"/>
  <img src="https://img.shields.io/badge/Cucumber-23D96C?style=for-the-badge&logo=cucumber&logoColor=white"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white"/>
  <img src="https://img.shields.io/badge/Allure-EB5A5A?style=for-the-badge"/>
  <img src="https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white"/>
</p>

____
<a id="cases"></a>
## ️ Test documentation
- [📊 Feature Matrix](https://docs.google.com/spreadsheets/d/11v5GXk0FmRQh8te88jC_ygIHz88kpFgUvTIgDxOT1ZY/edit?gid=1912652394)
- [📋 Project Board](https://github.com/orgs/RedRoverSchool/projects/3)

____
<a id="settings"></a>
## ️ Settings
To configure the project locally, follow these steps:

1. **Run Jenkins locally**
    - Install and start Jenkins on your machine.
    - Ensure all required plugins are installed.

2. **Configure the settings file**
    - Copy `local.properties.TEMPLATE`.
    - Rename it to `local.properties` (remove `TEMPLATE`).
    - Fill in the necessary values with your local configuration.

After completing these steps, your project should be ready to use! 🚀

____
## Running Tests Locally

To run automated tests locally, use the following commands:

1 **Run all tests:**
   - ```mvn test```
2 **Run specific tests:**
   - ```mvn test -Dtest=TestName```
3 **Run a specific test suite:**
   - ```mvn clean test -Dsurefire.suiteXmlFiles=suite/<suite-name>```
   Replace <suite-name> with the actual suite file name.

---
<a id="jenkins"></a>
## <img width="20" style="vertical-align:middle" title="Jenkins" src="media/logo/jenkins.svg"> </a> Сборка в <a target="_blank" href="https://jenkins.autotests.cloud/job/chitai-gorod-tests/"> Jenkins </a>
Для доступа в Jenkins необходима регистрация на ресурсе [Jenkins](https://jenkins.autotests.cloud/) Для запуска сборки необходимо перейти в раздел <code>Build with parameters</code>, выбрать необходимые параметры и нажать кнопку <code>Build</code>.
<p align="center">
<img title="jenkins" src="media/screenshots/screenshotsJenkins.png">
</p>
После выполнения сборки, в блоке <code>Build History</code> напротив номера сборки появятся значки <code>Allure Report</code>, при клике на которые откроется страница с сформированным html-отчетом.

____
<a id="allureReport"></a>
## <img width="30" style="vertical-align:middle" title="Allure Report" src="media/logo/allure.svg"> </a> Пример <a target="_blank" href="https://jenkins.autotests.cloud/job/chitai-gorod-tests/8/allure/"> Allure-отчета </a>
<p align="center">
<img title="Allure Report" src="media/screenshots/screenshotsAllure.png">
</p>

____
<a id="tg"></a>
## <img width="30" style="vertical-align:middle" title="Telegram" src="media/logo/telegram.svg"> Уведомления в Telegram с использованием бота
После завершения сборки, бот, созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с отчетом
о прогоне тестов в [специально настроенный чат](https://t.me/+m0gDb0Dy9ckwZTYy).
<div style="background-color: #18222d">
<p align="center">
<img width="40%" title="Telegram Notifications" src="media/screenshots/screenshotsTelegram.png">
</p>
</div>