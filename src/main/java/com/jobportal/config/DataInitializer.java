package com.jobportal.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jobportal.model.*;
import com.jobportal.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ApplicationRepository applicationRepository;
    private final MessageRepository messageRepository;
    private final AnnouncementRepository announcementRepository;
    private final FAQRepository faqRepository;
    private final ReviewRepository reviewRepository;
    private final CategoryRepository categoryRepository;
    private final NotificationRepository notificationRepository;
    private final CourseRepository courseRepository;

    public DataInitializer(
            UserRepository userRepository,
            JobRepository jobRepository,
            ApplicationRepository applicationRepository,
            MessageRepository messageRepository,
            AnnouncementRepository announcementRepository,
            FAQRepository faqRepository,
            ReviewRepository reviewRepository,
            CategoryRepository categoryRepository,
            NotificationRepository notificationRepository,
            CourseRepository courseRepository
    ) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.applicationRepository = applicationRepository;
        this.messageRepository = messageRepository;
        this.announcementRepository = announcementRepository;
        this.faqRepository = faqRepository;
        this.reviewRepository = reviewRepository;
        this.categoryRepository = categoryRepository;
        this.notificationRepository = notificationRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            System.out.println("Data already initialized in MySQL database.");
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;

        InputStream resourceStream = getClass().getResourceAsStream("/db.json");
        if (resourceStream != null) {
            System.out.println("Seeding database from classpath resource: /db.json");
            rootNode = mapper.readTree(resourceStream);
        } else {
            File dbFile = new File("db.json");
            if (!dbFile.exists()) {
                dbFile = new File("../db.json");
            }
            if (dbFile.exists()) {
                System.out.println("Seeding database from file: " + dbFile.getAbsolutePath());
                rootNode = mapper.readTree(dbFile);
            }
        }

        if (rootNode == null) {
            System.out.println("db.json not found in classpath or filesystem, skipping initial seeding.");
            return;
        }

        // Seed Users
        if (rootNode.has("users")) {
            for (JsonNode node : rootNode.get("users")) {
                User user = new User();
                user.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                user.setName(node.has("name") ? node.get("name").asText() : "");
                user.setEmail(node.has("email") ? node.get("email").asText() : "");
                user.setPassword(node.has("password") ? node.get("password").asText() : "");
                user.setRole(node.has("role") ? node.get("role").asText() : "seeker");
                if (node.has("profile") && !node.get("profile").isNull()) {
                    user.setProfile(node.get("profile").toString());
                }
                if (node.has("company") && !node.get("company").isNull()) {
                    user.setCompany(node.get("company").toString());
                }
                if (node.has("savedJobs") && !node.get("savedJobs").isNull()) {
                    user.setSavedJobs(node.get("savedJobs").toString());
                }
                userRepository.save(user);
            }
        }

        // Seed Jobs
        if (rootNode.has("jobs")) {
            for (JsonNode node : rootNode.get("jobs")) {
                Job job = new Job();
                job.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                job.setEmployerId(node.has("employerId") ? node.get("employerId").asText() : "");
                job.setCompanyName(node.has("companyName") ? node.get("companyName").asText() : "");
                job.setTitle(node.has("title") ? node.get("title").asText() : "");
                job.setDescription(node.has("description") ? node.get("description").asText() : "");
                job.setLocation(node.has("location") ? node.get("location").asText() : "");
                job.setJobType(node.has("jobType") ? node.get("jobType").asText() : "");
                job.setIndustry(node.has("industry") ? node.get("industry").asText() : "");
                job.setExperienceLevel(node.has("experienceLevel") ? node.get("experienceLevel").asText() : "");
                job.setSalaryRange(node.has("salaryRange") ? node.get("salaryRange").asText() : "");
                if (node.has("requiredSkills") && !node.get("requiredSkills").isNull()) {
                    job.setRequiredSkills(node.get("requiredSkills").toString());
                }
                job.setDeadline(node.has("deadline") ? node.get("deadline").asText() : "");
                job.setPostedAt(node.has("postedAt") ? node.get("postedAt").asText() : "");
                job.setStatus(node.has("status") ? node.get("status").asText() : "approved");
                job.setViews(node.has("views") ? node.get("views").asInt() : 0);
                jobRepository.save(job);
            }
        }

        // Seed Applications
        if (rootNode.has("applications")) {
            for (JsonNode node : rootNode.get("applications")) {
                Application app = new Application();
                app.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                app.setJobId(node.has("jobId") && !node.get("jobId").isNull() ? node.get("jobId").asText() : null);
                app.setJobTitle(node.has("jobTitle") ? node.get("jobTitle").asText() : "");
                app.setSeekerId(node.has("seekerId") ? node.get("seekerId").asText() : "");
                app.setSeekerName(node.has("seekerName") ? node.get("seekerName").asText() : "");
                app.setEmployerId(node.has("employerId") ? node.get("employerId").asText() : "");
                app.setStatus(node.has("status") ? node.get("status").asText() : "applied");
                app.setAppliedAt(node.has("appliedAt") ? node.get("appliedAt").asText() : "");
                applicationRepository.save(app);
            }
        }

        // Seed Messages
        if (rootNode.has("messages")) {
            for (JsonNode node : rootNode.get("messages")) {
                Message msg = new Message();
                msg.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                msg.setSenderId(node.has("senderId") ? node.get("senderId").asText() : "");
                msg.setSenderName(node.has("senderName") ? node.get("senderName").asText() : "");
                msg.setReceiverId(node.has("receiverId") ? node.get("receiverId").asText() : "");
                msg.setContent(node.has("content") ? node.get("content").asText() : "");
                msg.setTimestamp(node.has("timestamp") ? node.get("timestamp").asText() : "");
                messageRepository.save(msg);
            }
        }

        // Seed Announcements
        if (rootNode.has("announcements")) {
            for (JsonNode node : rootNode.get("announcements")) {
                Announcement ann = new Announcement();
                ann.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                ann.setMessage(node.has("message") ? node.get("message").asText() : "");
                ann.setDate(node.has("date") ? node.get("date").asText() : "");
                ann.setActive(node.has("active") ? node.get("active").asBoolean() : true);
                announcementRepository.save(ann);
            }
        }

        // Seed FAQs
        if (rootNode.has("faqs")) {
            for (JsonNode node : rootNode.get("faqs")) {
                FAQ faq = new FAQ();
                faq.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                faq.setQuestion(node.has("question") ? node.get("question").asText() : "");
                faq.setAnswer(node.has("answer") ? node.get("answer").asText() : "");
                faqRepository.save(faq);
            }
        }

        // Seed Reviews
        if (rootNode.has("reviews")) {
            for (JsonNode node : rootNode.get("reviews")) {
                Review rev = new Review();
                rev.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                rev.setEmployerId(node.has("employerId") ? node.get("employerId").asText() : "");
                rev.setSeekerId(node.has("seekerId") ? node.get("seekerId").asText() : "");
                rev.setRating(node.has("rating") ? node.get("rating").asInt() : 5);
                rev.setComment(node.has("comment") ? node.get("comment").asText() : "");
                rev.setCreatedAt(node.has("createdAt") ? node.get("createdAt").asText() : "");
                reviewRepository.save(rev);
            }
        }

        // Seed Categories
        if (rootNode.has("categories")) {
            for (JsonNode node : rootNode.get("categories")) {
                Category cat = new Category();
                cat.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                cat.setName(node.has("name") ? node.get("name").asText() : "");
                categoryRepository.save(cat);
            }
        }

        // Seed Notifications
        if (rootNode.has("notifications")) {
            for (JsonNode node : rootNode.get("notifications")) {
                Notification notif = new Notification();
                notif.setId(node.has("id") ? node.get("id").asText() : UUID.randomUUID().toString());
                notif.setUserId(node.has("userId") ? node.get("userId").asText() : "");
                notif.setMessage(node.has("message") ? node.get("message").asText() : "");
                notif.setIsRead(node.has("isRead") ? node.get("isRead").asBoolean() : false);
                notif.setType(node.has("type") ? node.get("type").asText() : "");
                notif.setCreatedAt(node.has("createdAt") ? node.get("createdAt").asText() : "");
                notificationRepository.save(notif);
            }
        }

        System.out.println("Seeding completed successfully!");
    }
}
