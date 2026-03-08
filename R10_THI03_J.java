// Rule 10: Thread APIs (THI)
// THI03_J: Always invoke wait() and await() methods inside a loop given the non-compliant code shown below:

synchronized (object) {
  if (<condition does not hold>) {
    object.wait();
  }
  // Proceed when condition holds
}

// Corrected code from the Compliant Solution shown below:

synchronized (object) {
  while (<condition does not hold>) {
    object.wait();
  }
  // Proceed when condition holds
}
