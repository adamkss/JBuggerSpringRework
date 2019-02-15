package com.adam.kiss.jbugger.ETC;

import com.adam.kiss.jbugger.entities.*;
import com.adam.kiss.jbugger.enums.PredefinedStatusNames;
import com.adam.kiss.jbugger.repositories.*;
import com.adam.kiss.jbugger.enums.NotificationType;
import com.adam.kiss.jbugger.enums.Severity;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;
import utils.FakeDataGenerator;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class SingletonInit {
    private final RightRepository rightRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final BugRepository bugRepository;
    private final NotificationRepository notificationRepository;
    private final StatusRepository statusRepository;
    private final LabelRepository labelRepository;

    @PostConstruct
    private void init() {

        labelRepository.save(new Label("Frontend","#ff0000"));
        labelRepository.save(new Label("Backend","#00ff00"));
        labelRepository.save(new Label("DevOps","#0000ff"));
        labelRepository.save(new Label("Testing","#ff00ff"));

        Status.PredefinedStatuses.PREDEFINED_STATUSES.forEach(
                (statusRepository::save)
        );

        rightRepository.save(Right.BUG_CLOSE);
        rightRepository.save(Right.BUG_EXPORT_PDF);
        rightRepository.save(Right.BUG_MANAGEMENT);
        rightRepository.save(Right.PERMISSION_MANAGEMENT);
        rightRepository.save(Right.USER_MANAGEMENT);


        Role.ADM_ROLE.getRights().add(Right.BUG_CLOSE);
        Role.ADM_ROLE.getRights().add(Right.BUG_EXPORT_PDF);
        Role.ADM_ROLE.getRights().add(Right.BUG_MANAGEMENT);
        Role.ADM_ROLE.getRights().add(Right.PERMISSION_MANAGEMENT);
        Role.ADM_ROLE.getRights().add(Right.USER_MANAGEMENT);

        Role.DEV_ROLE.getRights().add(Right.BUG_MANAGEMENT);
        Role.DEV_ROLE.getRights().add(Right.BUG_EXPORT_PDF);
        Role.DEV_ROLE.getRights().add(Right.BUG_CLOSE);

        roleRepository.save(Role.ADM_ROLE);
        roleRepository.save(Role.DEV_ROLE);
        roleRepository.save(Role.PM_ROLE);
        roleRepository.save(Role.TEST_ROLE);
        roleRepository.save(Role.TM_ROLE);

        User admin = new User();
        admin.setUsername("admin");
        admin.setPasswordHash(BCrypt.hashpw("admin", BCrypt.gensalt()));
        admin.setUserActivated(true);
        admin.setName("Admin Master");
        admin.setEmail("admin@msggroup.com");
        admin.getRoles().add(Role.ADM_ROLE);
        admin.setPhoneNumber("0040720170752");

        User tester = new User();
        tester.setUsername("tester");
        tester.setPasswordHash(BCrypt.hashpw("tester", BCrypt.gensalt()));
        tester.setUserActivated(true);
        tester.setName("Tester Mester");
        tester.setEmail("tester@msggroup.com");
        tester.getRoles().add(Role.TEST_ROLE);
        tester.setPhoneNumber("003145849999");

        User dev1 = new User();
        dev1.setUsername("dev1");
        dev1.setPasswordHash(BCrypt.hashpw("dev1", BCrypt.gensalt()));
        dev1.setUserActivated(true);
        dev1.setName("The DeveloperOne");
        dev1.setEmail("dev1@msggroup.com");
        dev1.getRoles().add(Role.DEV_ROLE);
        dev1.setPhoneNumber("004045849999");

        User dev2 = new User();
        dev2.setUsername("dev2");
        dev2.setPasswordHash(BCrypt.hashpw("dev2", BCrypt.gensalt()));
        dev2.setUserActivated(true);
        dev2.setName("Second Developer");
        dev2.setEmail("dev2@msggroup.com");
        dev2.getRoles().add(Role.DEV_ROLE);
        dev2.setPhoneNumber("004045839999");

        FakeDataGenerator.doGenerate(10, 20);
        FakeDataGenerator.getUSERS().forEach(user -> userRepository.save(user));
        FakeDataGenerator.getBUGS().forEach(bug -> bugRepository.save(bug));

        Notification notification1 = new Notification(NotificationType.WELCOME_NEW_USER, "Welcome admin!", LocalDateTime.now().minusDays(40));
        //Notification notification2 = new Notification(NotificationType.BUG_STATUS_UPDATED,"Bug 1 was updated!");
        admin.getNotifications().add(notification1);
        // admin.getNotifications().add(notification2);
        notificationRepository.save(notification1);
        //em.persist(notification2);

        userRepository.save(tester);
        userRepository.save(admin);
        userRepository.save(dev1);
        userRepository.save(dev2);

        bugRepository.save(new Bug("Buggy Facebook", "The facebook main page is quite buggy", "1.0", null, LocalDate.now(), Severity.CRITICAL, dev1, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(0), dev2));
        bugRepository.save(new Bug("Buggy Instagram", "The instagram main page is very buggy", "1.0", null, LocalDate.now(), Severity.CRITICAL, admin, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(1), dev1));
        bugRepository.save(new Bug("Buggy Twitter", "The twitter is buggy", "2.0", "2.0", LocalDate.now().minusDays(1), Severity.HIGH, admin, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(2), dev2));
        bugRepository.save(new Bug("Buggy Pinterest", "Pinterest is really buggy", "3.0", null, LocalDate.now().minusDays(10), Severity.LOW, admin, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(3), dev1));
        bugRepository.save(new Bug("Buggy Uber", "Uber doesn't work!", "4.0", null, LocalDate.now().minusDays(25), Severity.HIGH, tester, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(4), admin));
        bugRepository.save(new Bug("Bug in Autovit", "Autovit doesn't show the cars...", "1.0", null, LocalDate.now().minusDays(25), Severity.HIGH, tester, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(5), admin));
        bugRepository.save(new Bug("Bug in WizzAir", "WizzAir planes don't fly", "2.0.1", null, LocalDate.now().minusDays(14), Severity.CRITICAL, dev1, Status.PredefinedStatuses.PREDEFINED_STATUSES.get(0), dev1));
    }
}
