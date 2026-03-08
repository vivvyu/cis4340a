# Rule 02: Expressions (EXP)
# XP00-J: Do not ignore valued returned by methods. Given the noncompliant code below:
public void deleteFile(){

  File someFile = new File("someFileName.txt");
  // Do something with someFile
someFile.delete();
}

# Correct the code shown in the Compliant Solution below:
public void deleteFile(){

  File someFile = new File("someFileName.txt");
  // Do something with someFile
  if (!someFile.delete()) {
    // Handle failure to delete the file
  }

}

public class Replace {
  public static void main(String[] args) {
    String original = "insecure";
    original = original.replace('i', '9');
    System.out.println(original);
  }
}
