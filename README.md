# 📚 Stumate

> AI 기반 스마트 학습 관리 앱

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![Azure](https://img.shields.io/badge/Azure-App%20Service-0089D6)

---

## 📖 프로젝트 소개

Stumate는 학생들의 효율적인 학습을 돕는 AI 기반 스마트 학습 관리 앱입니다.
공부 타이머, 투두리스트, 고정 일정 관리 기능을 제공하며, Azure OpenAI(GPT-4o)를 활용해 개인 맞춤형 AI 플래너와 응원 메시지를 생성합니다.

---

## ✨ 주요 기능

### 👤 사용자
- 회원가입 / 로그인
- 플랜 난이도 설정 (일반인 / 독서실러 / 도서관귀신)
- 학습 정보 입력 (과목, 고정 일정)

### ⏱ 공부 타이머
- 과목 및 장소 선택 후 타이머 시작/종료
- 집중도 별점 평가 (1~5점)
- 일시정지 횟수 기록

### ✅ 투두리스트
- 날짜별 할 일 추가 / 완료 / 삭제
- 오늘 / 주간 할 일 조회

### 📅 고정 일정
- 복수 요일 선택 가능
- 고정 공부 일정 등록 / 삭제

### 📚 과목 관리
- 공부 과목 추가 / 삭제
- 중복 과목 방지

### 🤖 AI 플래너 (Azure OpenAI GPT-4o)
- 공부 패턴 분석 (시간대별 집중도, 장소별 효율, 과목별 집중도)
- 학습 유형 분석 (야행성 / 아침형 / 오전형 / 오후형 / 저녁형)
- 포모도로 분석 (평균 집중 유지 시간, 추천 사이클)
- 미완료 할 일 기반 AI 맞춤 학습 순서 추천

### 💬 AI 응원 메시지 (Azure OpenAI GPT-4o)
- 사용자 이름, 현재 시간대, 이번주 수행률 반영
- 앱 열 때마다 개인 맞춤 응원 메시지 생성

---

## 🛠 기술 스택

| 분류 | 기술 |
|---|---|
| Backend | Java 17, Spring Boot 3.x |
| Database | MySQL 8.0 (Azure MySQL Flexible Server) |
| ORM | Spring Data JPA / Hibernate |
| AI | Azure OpenAI (GPT-4o) |
| Deploy | Azure App Service |
| Docs | Swagger (SpringDoc OpenAPI) |
| Security | Spring Security, BCrypt |

---

## 📁 프로젝트 구조

```
src/main/java/com/Stumate/project/
├── domain/
│   ├── user/                    # 사용자 (회원가입, 로그인, 플랜 설정)
│   ├── userSubject/             # 공부 과목 관리
│   ├── fixedSchedule/           # 고정 일정 관리
│   ├── todo/                    # 투두리스트
│   ├── studySession/            # 공부 타이머 세션
│   ├── planner/                 # AI 플래너
│   └── motivationalMessage/     # AI 응원 메시지
└── global/
    ├── config/                  # Security, Swagger, Azure OpenAI, CORS 설정
    └── exception/               # 글로벌 예외 처리
```

---

## 📡 API 명세

Swagger UI:
```
https://stumate-gmf9edeabxc3ahhf.koreacentral-01.azurewebsites.net/swagger-ui/index.html
```

### 👤 사용자
| Method | URL | 설명 |
|---|---|---|
| POST | /api/user/signup | 회원가입 |
| POST | /api/user/auth/login | 로그인 |
| POST | /api/user/auth/logout | 로그아웃 |
| PATCH | /api/user/{userId}/plan | 플랜 난이도 업데이트 |

### 📚 과목
| Method | URL | 설명 |
|---|---|---|
| GET | /api/users/{userId}/subjects | 과목 목록 조회 |
| POST | /api/users/{userId}/subjects | 과목 추가 |
| DELETE | /api/users/{userId}/subjects/{userSubjectId} | 과목 삭제 |

### 📅 고정 일정
| Method | URL | 설명 |
|---|---|---|
| GET | /api/users/{userId}/schedules | 고정 일정 조회 |
| POST | /api/users/{userId}/schedules | 고정 일정 추가 (복수 요일 지원) |
| DELETE | /api/users/{userId}/schedules/{scheduleId} | 고정 일정 삭제 |

### ✅ 투두리스트
| Method | URL | 설명 |
|---|---|---|
| GET | /api/users/{userId}/todos/today | 오늘 할 일 조회 |
| GET | /api/users/{userId}/todos/weekly | 주간 할 일 조회 |
| POST | /api/users/{userId}/todos | 할 일 추가 |
| PATCH | /api/users/{userId}/todos/{todoId}/complete | 할 일 완료 |
| DELETE | /api/users/{userId}/todos/{todoId} | 할 일 삭제 |

### ⏱ 타이머 세션
| Method | URL | 설명 |
|---|---|---|
| POST | /api/users/{userId}/sessions/start | 타이머 시작 |
| PATCH | /api/users/{userId}/sessions/{sessionId}/finish | 타이머 종료 |
| GET | /api/users/{userId}/sessions/weekly-stats | 주간 공부 통계 |

### 🤖 AI
| Method | URL | 설명 |
|---|---|---|
| GET | /api/users/{userId}/planner | AI 플래너 조회 |
| GET | /api/users/{userId}/message | AI 응원 메시지 조회 |

---

## ⚙️ 환경 변수

Azure App Service 환경 변수에 아래 값을 설정해주세요:

| 변수명 | 설명 |
|---|---|
| `DB_URL` | MySQL 연결 URL |
| `DB_USERNAME` | DB 사용자명 |
| `DB_PASSWORD` | DB 비밀번호 |
| `AZURE_OPENAI_ENDPOINT` | Azure OpenAI 엔드포인트 |
| `AZURE_OPENAI_KEY` | Azure OpenAI API 키 |
| `AZURE_OPENAI_DEPLOYMENT` | 배포 모델명 (gpt-4o) |

---

## 👩‍💻 개발자

| 이름 | 역할 | 학교 |
|---|---|---|
| 양세영 | Backend 개발 | 숙명여자대학교 |

---

## 📄 라이선스

숙명여자대학교 졸업 프로젝트 (인공지능산업체특강)
