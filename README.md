# AI Banking Test Automation

End-to-end test automation for [ParaBank](https://parabank.parasoft.com) using Claude AI for dynamic test data generation.

## How It Works

Instead of hardcoded test data, Claude AI generates creative and varied test cases at runtime. Selenium then executes them against the ParaBank demo application.

Claude API → generates test data
Selenium   → executes tests
TestNG     → manages test lifecycle

## Tech Stack

| Tool | Purpose |
|------|---------|
| Java 23 | Programming language |
| Selenium 4 | Browser automation |
| TestNG | Test framework |
| Claude API | AI test data generation |
| Maven | Build tool |
| OkHttp | HTTP client for Claude API |

## Project Structure

src/
├── main/java/com/aibanking/
│   ├── config/        # Configuration (URLs, API keys)
│   ├── pages/         # Page Objects (LoginPage, TransferFundsPage)
│   └── utils/         # DriverManager (WebDriver lifecycle)
└── test/java/com/aibanking/
├── ai/            # Claude client + test data generator
└── tests/         # LoginTest, TransferFundsTest

## Setup

### Prerequisites
- Java 23
- Maven
- Chrome browser
- Claude API key from [console.anthropic.com](https://console.anthropic.com)

### Run

```bash
export CLAUDE_API_KEY=your_key_here
mvn test
```

## Key Concepts

**Page Object Model (POM)** — each page is a separate class with its own locators and actions.

**Singleton Pattern** — one WebDriver instance shared across all tests.

**AI-Generated Test Data** — Claude generates varied scenarios including edge cases, invalid inputs, and boundary values each time tests run.
