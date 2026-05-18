package com.aibanking.config;

public class Config {

    public static final String BASE_URL = "https://parabank.parasoft.com/parabank";

    public static final String BROWSER = "chrome";

    public static final String CLAUDE_API_KEY = System.getenv("CLAUDE_API_KEY");
    public static final String CLAUDE_MODEL = "claude-sonnet-4-5";
    public static final String CLAUDE_API_URL = "https://api.anthropic.com/v1/messages";

    public static final int WAIT_TIMEOUT = 10;

    public static final String DEFAULT_USERNAME = "john";
    public static final String DEFAULT_PASSWORD = "demo";

    private Config() {}
}