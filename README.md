# 🎯 PrepLakshya - AI Powered Placement Preparation Tracker

## 📌 Project Overview
PrepLakshya is an AI-powered backend system built for engineering students
to track and improve their placement preparation.

## 🚀 Features
- ✅ User Registration & Login
- ✅ AI-Generated Study Roadmap (Google Gemini AI)
- ✅ Topic Tracker (Mark topics as complete)
- ✅ Progress Dashboard (Track % completion)
- ✅ Target Company based preparation

## 🛠️ Tech Stack
- Java 17
- Spring Boot 3.5
- MySQL Database
- Hibernate ORM
- Google Gemini AI API
- REST APIs

## 📡 API Endpoints
| Method | URL | Description |
|--------|-----|-------------|
| POST | /api/users/register | Register new user |
| POST | /api/users/login | User login |
| GET | /api/roadmap/generate | Generate AI roadmap |
| POST | /api/topics/add | Add study topic |
| PUT | /api/topics/complete/{id} | Mark topic complete |
| GET | /api/topics/progress/{userId} | Get progress % |
| GET | /api/dashboard/{userId} | Get dashboard |

## 👨‍💻 Developer
**Sudheer Kumar Kuparala**
B.Tech - Information Technology
Pragati Engineering College