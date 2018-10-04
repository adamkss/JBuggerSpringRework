package utils;

import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.enums.Severity;
import com.adam.kiss.jbugger.enums.Status;
import com.adam.kiss.jbugger.exceptions.UserNotValidException;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FakeDataGenerator {
    private static String[] FIRST_NAMES;
    private static String[] LAST_NAMES;
    private static String[] PHONE_PREFIXES;
    private static String[] BUG_TITLES_PREFIX;
    private static String[] BUG_TITLES_SUBJECT;
    private static String[] BUG_DESCRIPTIONS;

    private static List<User> USERS = new ArrayList<>();
    private static List<Bug> BUGS = new ArrayList<>();

    static {
        FIRST_NAMES = new String[]{"Lavinia", "Andreea", "Andrei", "Robert", "Paul", "John", "Viktoria", "Adam", "Akos",
                "Nandor", "Szabolcs", "Emanuel", "Alexandru", "Denisa", "Florin", "Steffen", "Teodora", "Laura", "Barbara",
                "Adriana", "Ana"};
        LAST_NAMES = new String[]{"Ciuhuta", "Rus", "Gal", "Kiss", "Cosma", "Zbiera", "Vintila", "Knall", "Precup", "Coste", "Dulau", "Maximilian",
                "Nagy", "Kerekes", "Lepsa", "Lazar", "Neagu", "Pojar", "Varga", "Mindru"};
        PHONE_PREFIXES = new String[]{"0040", "0049", "+40", "+49"};
        BUG_TITLES_PREFIX = new String[]{"Buggy ", "Bug in ", "Bug found in ", "There's a bug in "};
        BUG_TITLES_SUBJECT = new String[]{"Pinterest", "Facebook", "Instagram", "Snapchat", "Twitter", "Uber", "msg.de", "google.com", "backend code", "Javascript promises", "Lollapalooza",
                "Messenger", "Whatsapp", "CFR Calatori", "Waze", "Sheets", "Measure", "Autovit", "BT24"};
        BUG_DESCRIPTIONS = new String[]{"Integer in sollicitudin urna. Fusce pharetra neque at dui laoreet, nec consequat ex semper.",
                "Duis sem quam, laoreet nec elementum ut, tincidunt nec augue.",
                "Aliquam nec tincidunt ante. Vestibulum ante ipsum.",
                "Quisque venenatis erat at."};

    }

    private static User generateNewUser() {
        User user = new User();
        Random generator = new Random();
        String prenume = FIRST_NAMES[generator.nextInt(FIRST_NAMES.length)];
        String nume = LAST_NAMES[generator.nextInt(LAST_NAMES.length)];
        user.setName(prenume + " " + nume);

        user.setEmail(prenume.toLowerCase() + nume + "@msggroup.com");

        StringBuilder phoneNumberBuilder = new StringBuilder();
        phoneNumberBuilder.append(PHONE_PREFIXES[generator.nextInt(PHONE_PREFIXES.length)]);
        for (int i = 1; i <= 10; i++) {
            phoneNumberBuilder.append(generator.nextInt(10));
        }
        user.setPhoneNumber(phoneNumberBuilder.toString());
        String userName = null;
        try {
            userName = UserHelper.generateUserName(nume, prenume, 5);
        } catch (UserNotValidException e) {
            e.printStackTrace();
        }
        user.setUsername(userName);

        user.setPasswordHash(BCrypt.hashpw(userName, BCrypt.gensalt()));

        user.setUserActivated(true);

        user.getRoles().add(Role.DEV_ROLE);

        return user;
    }

    private static Bug generateNewBug() {
        Bug bug = new Bug();
        Random random = new Random();
        bug.setTitle(BUG_TITLES_PREFIX[random.nextInt(BUG_TITLES_PREFIX.length)]+BUG_TITLES_SUBJECT[random.nextInt(BUG_TITLES_SUBJECT.length)]);
        bug.setDescription(BUG_DESCRIPTIONS[random.nextInt(BUG_DESCRIPTIONS.length)]);
        bug.setStatus(Status.values()[random.nextInt(Status.values().length)]);
        bug.setSeverity(Severity.values()[random.nextInt(Severity.values().length)]);
        bug.setRevision(random.nextInt(10)+"."+random.nextInt(10));
        bug.setTargetDate(LocalDate.now().plusDays(random.nextInt(100)));
        bug.setCreatedBy(USERS.get(random.nextInt(USERS.size())));
        bug.setAssignedTo(USERS.get(random.nextInt(USERS.size())));

        return bug;
    }

    public static void doGenerate(int nrUsers,int nrBugs){
        for(int i=0;i<nrUsers;i++){
            USERS.add(generateNewUser());
        }
        for(int i=0;i<nrBugs;i++){
            BUGS.add(generateNewBug());
        }
    }

    public static List<User> getUSERS() {
        return USERS;
    }

    public static List<Bug> getBUGS() {
        return BUGS;
    }
}
