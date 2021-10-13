package aslan.aslanov.drops.util;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;

public class UtilConstant {
    public static final int SENDER = 8215;
    public static final int RECEIVER = 5064;
    public static final String MESSAGE_CHANNEL = "messages";

  /*  private static void updateUI(FirebaseUser currentUser) {
        if (currentUser != null) {
            // Name, email address, and profile photo Url
            String name = currentUser.getDisplayName();
            String email = currentUser.getEmail();
            Uri photoUrl = currentUser.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = currentUser.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = currentUser.getUid();
        }
    }*/
}
