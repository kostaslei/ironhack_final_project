# ironhack_final_project

# 🌆 CollabCity BCN

## 🧩 Description of the Project

**CollabCity BCN** is a community-driven platform for **developers, designers, artists, and startup founders** based in **Barcelona** to connect, collaborate, and bring creative or technical projects to life.

Users can:
- 👥 Create profiles highlighting their skills and interests  
- 💬 Post updates, ideas, or requests (like a social feed)  
- 💡 Start **Projects** and recruit collaborators  
- 💌 Send direct messages and friend requests  
- 🧠 Build teams for tech, art, and design startups  

The app aims to strengthen Barcelona’s creative and tech ecosystem by connecting people who want to **build things together** — from small indie games to large-scale apps or design initiatives.

---

## 🧱 UML Class Diagram

+------------------------------------+
| User |
+------------------------------------+
| - id: Long |
| - username: String |
| - email: String |
| - password: String |
| - role: String |
| - bio: String |
| - skills: String |
| - city: String |
| - profileImage: String |
+------------------------------------+
| +getFriends(): List<User> |
| +getProjects(): List<Project> |
+------------------------------------+
1
|
| has many
|
v
+------------------------------------+
| Post |
+------------------------------------+
| - id: Long |
| - content: String |
| - timestamp: LocalDateTime |
| - imageUrl: String |
| - likes: int |
+------------------------------------+
| * postedBy: User |
| * comments: List<Comment> |
+------------------------------------+

markdown
Copy code
         1
         |
         | has many
         |
         v
+------------------------------------+
| Comment |
+------------------------------------+
| - id: Long |
| - text: String |
| - timestamp: LocalDateTime |
+------------------------------------+
| * author: User |
| * post: Post |
+------------------------------------+

markdown
Copy code
         1
         |
         | owns
         |
         v
+------------------------------------+
| Project |
+------------------------------------+
| - id: Long |
| - title: String |
| - description: String |
| - category: String |
| - status: String |
| - createdAt: LocalDateTime |
+------------------------------------+
| * owner: User |
| * members: List<User> |
+------------------------------------+

User (1) ─── (M) Message
|
v
+------------------------------------+
| Message |
+------------------------------------+
| - id: Long |
| - content: String |
| - timestamp: LocalDateTime |
| - isRead: boolean |
+------------------------------------+
| * sender: User |
| * receiver: User |
+------------------------------------+

User (M) ─── (M) User
Friendship (Friendship)
+------------------------------------+
| Friendship |
+------------------------------------+
| - id: Long |
| - status: String [PENDING/ACCEPTED]|
+------------------------------------+
| * requester: User |
| * receiver: User |
+------------------------------------+

yaml
Copy code

---

## ⚙️ Setup

### 🪄 Prerequisites
- Java 17+
- Maven or Gradle
- MySQL installed locally
- Postman or cURL for API testing

🧰 Technologies Used
Layer	Technology
Backend Framework	Spring Boot 3 (Java 17)
Database	MySQL + JPA (Hibernate)
Security	Spring Security + JWT (Bearer Tokens)
API Design	RESTful API architecture
Version Control	Git + GitHub
Future Frontend Integration	React / Next.js 

🧭 Controllers and Routes Structure
Controller	Route Prefix	Description
AuthController	/api/auth	Register and Login endpoints
UserController	/api/users	Get user profiles, update bios, skills, etc.
PostController	/api/posts	CRUD for posts and comments
ProjectController	/api/projects	Create and manage collaboration projects
MessageController	/api/messages	Send and retrieve private messages
FriendshipController	/api/friends	Manage friend requests and connections

🔗 Extra Links

📑 Presentation Slides: 

🧑‍💻 GitHub Repository: https://github.com/kostaslei/ironhack_final_project

🧭 Future Work
✅ Add frontend using React or Vue.js

✅ Enable real-time messaging with WebSockets

✅ Implement AI skill matching to recommend collaborators

✅ Add notifications for messages and project updates

✅ Add OAuth (Google/GitHub) login

✅ Add search filters for projects by category or skill

📚 Resources
Spring Boot Official Documentation

Spring Security Guide

JWT Authentication in Spring Boot

MySQL Reference Manual

draw.io for UML Diagrams

Ironhack Java Bootcamp Resources

👨‍💻 Author: Konstantinos Leivaditis
🏙️ Location: Barcelona
📆 Ironhack Final Project – 2025