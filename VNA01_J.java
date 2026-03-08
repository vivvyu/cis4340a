// Rule 08: Visibility and Atomicity (VNA)
// VNA01_J: Ensure visibility of shared references to immutable objects given the non-compliant code below:
// Immutable Helper
public final class Helper {
  private final int n;

  public Helper(int n) {
    this.n = n;
  }
  // ...
}
final class Foo {
  private Helper helper;

  public Helper getHelper() {
    return helper;
  }

  public void setHelper(int num) {
    helper = new Helper(num);
  }
}

// Corrected code from the Compliant Solution (synchronization) shown below:
final class Foo {
  private Helper helper;

  public synchronized Helper getHelper() {
    return helper;
  }

  public synchronized void setHelper(int num) {
    helper = new Helper(num);
  }
}

// Compliant Solution: Volatile
final class Foo {
  private volatile Helper helper;

  public Helper getHelper() {
    return helper;
  }

  public void setHelper(int num) {
    helper = new Helper(num);
  }
}

// Compliant solution: java.util.concurrent utilities
final class Foo {
  private final AtomicReference<Helper> helperRef =
      new AtomicReference<Helper>();

  public Helper getHelper() {
    return helperRef.get();
  }

  public void setHelper(int num) {
    helperRef.set(new Helper(num));
  }
}
