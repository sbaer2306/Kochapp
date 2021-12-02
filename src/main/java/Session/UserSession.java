package Session;

import Datastructures.UserModel;

public class UserSession {
    private static UserModel activeUserSession = null; // activeUserSession ist global, alle UserSession-Objekte greifen auf dieselbe Variable zu

    public UserModel getUserSession() {
        return activeUserSession;
    } //gibt den angemeldeten User zurück

    public boolean sessionExists(){
        return (activeUserSession != null);
    } // überprüft, ob ein User angemeldet ist

    public void loginSession(UserModel user){
        activeUserSession = user;
    } // meldet einen User an

    public void logoutSession(){
        activeUserSession = null;
    } // meldet den User ab
}
