# Rule 00: Input Validation and Data Sanitization (IDS)
# IDSS03-J: Do not log unsanitized user input. Given the non-complaint code below:
if (loginSuccessful) {
  logger.severe("User login succeeded for: " + username);
} else {
  logger.severe("User login failed for: " + username);
}

# Correct the code shown in the Complaint Solution below:
if (loginSuccessful) {
  logger.severe("User login succeeded for: " + sanitizeUser(username));
} else {
  logger.severe("User login failed for: " + sanitizeUser(username));
}
public String sanitizeUser(String username) {
  return Pattern.matches("[A-Za-z0-9_]+", username)
      ? username : "unauthorized user";
}
Logger sanLogger = new SanitizedTextLogger(logger);

if (loginSuccessful) {
  sanLogger.severe("User login succeeded for: " + username);
} else {
  sanLogger.severe("User login failed for: " + username);
}
class SanitizedTextLogger extends Logger {
  Logger delegate;

  public SanitizedTextLogger(Logger delegate) {
    super(delegate.getName(), delegate.getResourceBundleName());
    this.delegate = delegate;
  }

  public String sanitize(String msg) {
    Pattern newline = Pattern.compile("\n");
    Matcher matcher = newline.matcher(msg);
    return matcher.replaceAll("\n  ");
  }

  public void severe(String msg) {
    delegate.severe(sanitize(msg));
  }

  // .. Other Logger methods which must also sanitize their log messages
}
