// Rule 09: Locking 
// LCK09_J: Do not perform operations that can block while holding a lock given the non-compliant solution below
// Non-compliant code example (Network I/O):

// Class Page is defined separately. 
// It stores and returns the Page name via getName()
Page[] pageBuff = new Page[MAX_PAGE_SIZE];

public synchronized boolean sendPage(Socket socket, String pageName) 
                                     throws IOException {
  // Get the output stream to write the Page to
  ObjectOutputStream out 
      = new ObjectOutputStream(socket.getOutputStream());

  // Find the Page requested by the client
  // (this operation requires synchronization)
  Page targetPage = null;
  for (Page p : pageBuff) {
    if (p.getName().compareTo(pageName) == 0) {
      targetPage = p;
    }
  }

  // Requested Page does not exist
  if (targetPage == null) {
    return false;
  }

  // Send the Page to the client
  // (does not require any synchronization)
  out.writeObject(targetPage);

  out.flush();
  out.close();
  return true;
}

// Corrected code from the Compliant Solution shown below:

// No synchronization
public boolean sendPage(Socket socket, String pageName) {
  Page targetPage = getPage(pageName);

  if (targetPage == null){
    return false;
  }
  return deliverPage(socket, targetPage);
}

// Requires synchronization
private synchronized Page getPage(String pageName) {
  Page targetPage = null;

  for (Page p : pageBuff) {
    if (p.getName().equals(pageName)) {
      targetPage = p;
    }
  }
  return targetPage;
}

// Return false if an error occurs, true if successful
public boolean deliverPage(Socket socket, Page page) {
  ObjectOutputStream out = null;
  boolean result = true;
  try {
    // Get the output stream to write the Page to
    out = new ObjectOutputStream(socket.getOutputStream());

    // Send the page to the client
    out.writeObject(page);out.flush();
  } catch (IOException io) {
    result = false;
  } finally {
    if (out != null) {
      try {
        out.close();
      } catch (IOException e) {
        result = false;
      }
    }
  }
  return result;
}



