/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */
package ai.agenticity.cv.generator;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.sql.SQLException;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;
import ai.agenticity.cv.generator.domain.Achievement;
import ai.agenticity.cv.generator.domain.CVTranslations;
import ai.agenticity.cv.generator.domain.Duty;
import ai.agenticity.cv.generator.domain.TechnicalEnvironment;
import ai.agenticity.cv.generator.domain.TechnicalEnvironmentCategory;
import ai.agenticity.cv.generator.domain.WorkExperience;

import java.util.ArrayList;
import java.util.Date;

/**
 * CV Generator for Francesco Bullini - CTO / AI Architect
 */
public class CV_java_to_PDF_Generator {

    // Language parameter: "EN" for English, "FR" for French
    public static final String LANGUAGE = "EN";
    public static final String JOB_TITLE = "CHIEF TECHNOLOGY OFFICER - AI EXPERT";

    // Job type configuration
    public static final boolean IS_FREELANCE_RELEVANT = false;  // true = Freelance, false = CDI
    public static final boolean REQUIRES_JAVA = false; // true = Java required, false = not required
    public static final boolean TARGET_COMPANY_IS_A_STARTUP = false; // true = The target company is a startup, or it's valuable to highlight my work in startups; false if it is not valuable for that company job description
    public static final boolean TARGET_COMPANY_IS_FROM_SCRATCH = false; // true = The target company is a startup from scratch, or it's valuable to highlight my work in startups from scratch;false if it is not valuable for that company job description
    public static final boolean TARGET_SECTOR_IS_EDUCATION_OR_EMPLOYABILITY_OR_SKILLS = true; // true = The target sector is someway related to education, employability or reskilling, upskilling or human resources; false if it is not valuable for that company job description

    public static final boolean INTRO_ENABLED = true; // true = Show introduction section, false = hide it
    public static final boolean PROFILE_ENABLED = true; // true = Show Profile section, false = hide it
    public static final boolean COMPETENCE_SECTION_ENABLED = false; // true = Show Key Competencies section, false = hide it
    public static final boolean DISPLAY_BUSINESS_SECTOR_ENABLED = false; // true = Show company business sector, false = hide it
    public static final boolean DUTIES_ENABLED = false; // true = Show Duties for work experiences, false = hide it
    public static final boolean TECH_ENV_SECTION_ENABLED = true; // true = Show Technical Environment for work experiences, false = hide it

    public static String CV_FILENAME = "CV_BULLINI_" +"CTO_"+ LANGUAGE + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".pdf";

    // Personal information
    public static final String FULL_NAME = "Francesco Bullini";
    public static final String EMAIL = IS_FREELANCE_RELEVANT ? "f@agenticity.com" : "fbullini@gmail.com";
    public static final String PHONE = "+33 (0)6 01 10 28 58";
    public static final String LINKEDIN = "https://www.linkedin.com/in/fbullini";
    public static final String ADDRESS = "92100 Boulogne-Billancourt, FR";

    public static final String IMG_BOX = "info.png";
    public static final String RESOURCE = "info.png";

    public static final float SPACING_BEFORE = 10;
    public static final float SPACING_AFTER = 10;

    // Font configuration - Using Arial font with Unicode support
    public static Font font_12;
    public static Font font_11;
    public static Font font_10;
    public static Font font_bold_11;
    public static Font font_bold_10;
    public static Font font_bold_12;
    private static CV_java_to_PDF_Generator CV_Generator;

    private static void addIfNotEmpty(Phrase ph, Chunk chunk){
       if (chunk!=null && !chunk.getContent().isEmpty())
           ph.add(chunk);
    }

    static {
        try {
            // Register Arial font from system
            FontFactory.register("/usr/share/fonts/truetype/liberation/LiberationSans-Regular.ttf", "Arial");
            FontFactory.register("/usr/share/fonts/truetype/liberation/LiberationSans-Bold.ttf", "Arial-Bold");

            // Create fonts with Unicode encoding
            font_12 = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
            font_11 = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11);
            font_10 = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
            font_bold_11 = FontFactory.getFont("Arial-Bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 11);
            font_bold_10 = FontFactory.getFont("Arial-Bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
            font_bold_12 = FontFactory.getFont("Arial-Bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 12);
        } catch (Exception e) {
            // Fallback to Helvetica if Arial is not available
            System.err.println("Arial font not found, falling back to Helvetica: " + e.getMessage());
            font_12 = new Font(Font.FontFamily.HELVETICA, 12);
            font_11 = new Font(Font.FontFamily.HELVETICA, 11);
            font_10 = new Font(Font.FontFamily.HELVETICA, 10);
            font_bold_11 = new Font(Font.FontFamily.HELVETICA, 11, Font.BOLD);
            font_bold_10 = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            font_bold_12 = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
        }
    }

    private CVTranslations translations;

    public void createPdf(String filename, String language) throws DocumentException, IOException, SQLException {
        // Initialize translations
        this.translations = new CVTranslations(language);
        CVTranslations translations = this.translations;

        Document document = new Document(PageSize.A4);
        FileOutputStream fos = new FileOutputStream(filename);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        document.open();

        writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
        PdfOutline root = writer.getRootOutline();

        // Add profile photo in top right corner
        try {
            Image photo = Image.getInstance("src/main/resources/me_job_no_sfondo_conf.jpeg");
            // Scale the image to appropriate size (approximately 100x120 points)
            photo.scaleToFit(100f, 120f);
            // Position in top right corner aligned with the top of the name "Francesco Bullini"
            // A4 page is 595x842 points, default margin is 36 points
            // X: 595 - 100 (image width) - 50 (margin) = 445
            // Y: Aligned with the top of the letters in the name
            photo.setAbsolutePosition(445f, 703f);
            document.add(photo);
        } catch (Exception e) {
            System.err.println("Warning: Could not load profile photo: " + e.getMessage());
        }

        // PERSONAL INFORMATION HEADER
        Font font_name_16 = FontFactory.getFont("Arial-Bold", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 16);
        Paragraph parName = new Paragraph(FULL_NAME, font_name_16);
        parName.setAlignment(Element.ALIGN_LEFT);
        parName.setSpacingAfter(5);

        Paragraph parContact = new Paragraph();
        Font linkFont = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.UNDERLINE, BaseColor.BLUE);
        Chunk linkedinChunk = new Chunk(LINKEDIN + "\n", linkFont);
        linkedinChunk.setAnchor(LINKEDIN);
        parContact.add(linkedinChunk);
        Chunk emailChunk = new Chunk(EMAIL + "\n", linkFont);
        emailChunk.setAnchor("mailto:" + EMAIL);
        parContact.add(emailChunk);
        parContact.add(new Chunk(PHONE + "\n", font_10));
        parContact.add(new Chunk(ADDRESS, font_10));
        parContact.setAlignment(Element.ALIGN_LEFT);
        parContact.setSpacingAfter(15);

        document.add(parName);
        document.add(parContact);

        // Title
        Paragraph par1_title = new Paragraph(translations.get("main_title") + "\n", font_bold_12);
        applyTitleStyle(par1_title);

        // Introduction
        Paragraph par1_intro = new Paragraph();
        Phrase phr1 = new Phrase();
        //TODO Chunks should be wrapped from a more descriptive Entity, with metadata that allow th system to xhose which chunk to print dependending from the input paramter; or which language to use for each chunk
        for (int i = 1; i <= 10; i++) {
            String key = "intro_" + i;
            String translation = translations.get(key);
            // Add the chunk only if the translation exists and is not the key itself
            if (translation != null && !translation.equals(key)  && !translation.trim().isEmpty()) {
                if (translation.contains("**")) {
                    String[] parts = translation.split("\\*\\*");
                    for (int j = 0; j < parts.length; j++) {
                        if (j % 2 == 1) {
                            phr1.add(new Chunk(parts[j], font_bold_10));
                        } else {
                            phr1.add(new Chunk(parts[j], font_10));
                        }
                    }
                } else {
                    phr1.add(new Chunk(translation, font_10));
                }
            }
        }
        phr1.setFont(font_10);
        par1_intro.add(phr1);

        // Profile
        Paragraph par2_title = new Paragraph(translations.get("profile_title"), font_bold_12);
        applyTitleStyle(par2_title);
        Paragraph par2_profile = new Paragraph();

        Phrase phr2_1 = new Phrase();
        Chunk chunk2_1 = new Chunk(translations.get("profile_1"), font_10);
        Chunk chunk2_2 = new Chunk(translations.get("profile_2"), font_10);
        Chunk chunk2_3 = new Chunk(translations.get("profile_3"), font_10);
        Chunk chunk2_4 = new Chunk(translations.get("profile_4"), font_10);

        addIfNotEmpty(phr2_1,chunk2_1);
        addIfNotEmpty(phr2_1,chunk2_2);
        addIfNotEmpty(phr2_1,chunk2_3);
        addIfNotEmpty(phr2_1,chunk2_4);
        phr2_1.setFont(font_10);
        par2_profile.add(phr2_1);

        // Key Competencies
        Paragraph par_comp_title = new Paragraph(translations.get("competencies_title"), font_bold_12);
        applyTitleStyle(par_comp_title);
        Paragraph par_competencies = new Paragraph();

        Phrase phr_comp = new Phrase();

        Chunk comp_mgmt_title = new Chunk(translations.get("comp_mgmt_title"));
        comp_mgmt_title.setFont(font_bold_11);
        Chunk comp_mgmt = new Chunk(translations.get("comp_mgmt"));
        comp_mgmt.setFont(font_10);

        Chunk comp_ai_title = new Chunk(translations.get("comp_ai_title"));
        comp_ai_title.setFont(font_bold_11);
        Chunk comp_ai = new Chunk(translations.get("comp_ai"));
        comp_ai.setFont(font_10);

        Chunk comp_tech_title = new Chunk(translations.get("comp_tech_title"));
        comp_tech_title.setFont(font_bold_11);
        Chunk comp_tech = new Chunk(translations.get("comp_tech"));
        comp_tech.setFont(font_10);

        Chunk comp_arch_title = new Chunk(translations.get("comp_arch_title"));
        comp_arch_title.setFont(font_bold_11);
        Chunk comp_arch = new Chunk(translations.get("comp_arch"));
        comp_arch.setFont(font_10);

        Chunk comp_cloud_ia_title = new Chunk(translations.get("comp_cloud_ia_title"));
        comp_cloud_ia_title.setFont(font_bold_11);
        Chunk comp_cloud_ia = new Chunk(translations.get("comp_cloud_ia"));
        comp_cloud_ia.setFont(font_10);

        Chunk comp_cloud_title = new Chunk(translations.get("comp_cloud_title"));
        comp_cloud_title.setFont(font_bold_11);
        Chunk comp_cloud = new Chunk(translations.get("comp_cloud"));
        comp_cloud.setFont(font_10);

        Chunk comp_front_title = new Chunk(translations.get("comp_front_title"));
        comp_front_title.setFont(font_bold_11);
        Chunk comp_front = new Chunk(translations.get("comp_front"));
        comp_front.setFont(font_10);

        Chunk comp_sec_title = new Chunk(translations.get("comp_sec_title"));
        comp_sec_title.setFont(font_bold_11);
        Chunk comp_sec = new Chunk(translations.get("comp_sec"));
        comp_sec.setFont(font_10);

        Chunk comp_dev_title = new Chunk(translations.get("comp_dev_title"));
        comp_dev_title.setFont(font_bold_11);
        Chunk comp_dev = new Chunk(translations.get("comp_dev"));
        comp_dev.setFont(font_10);

        phr_comp.add(comp_mgmt_title);
        phr_comp.add(comp_mgmt);
        phr_comp.add(comp_ai_title);
        phr_comp.add(comp_ai);
        phr_comp.add(comp_tech_title);
        phr_comp.add(comp_tech);
        phr_comp.add(comp_arch_title);
        phr_comp.add(comp_arch);
        phr_comp.add(comp_cloud_title);
        phr_comp.add(comp_cloud);
        phr_comp.add(comp_cloud_ia_title);
        phr_comp.add(comp_cloud_ia);
        phr_comp.add(comp_front_title);
        phr_comp.add(comp_front);
        phr_comp.add(comp_sec_title);
        phr_comp.add(comp_sec);
        phr_comp.add(comp_dev_title);
        phr_comp.add(comp_dev);

        par_competencies.add(phr_comp);

        // Work Experience List
        java.util.List<WorkExperience> workExperiences = new ArrayList<WorkExperience>();
        boolean isFrench = "FR".equals(language);

        // Work Experience 1: Agenticity.ai
        java.util.List<Duty> duties1 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements1 = new ArrayList<Achievement>();
        String oneLineSummary1 = "";
        boolean useOneLineSummary1 = true;

        if (isFrench) {
            duties1.add(new Duty("►Conception et développement d'infrastructure logicielle native en IA."));
            achievements1.add(new Achievement("►Conception et développement d'une infrastructure logicielle native en IA, centrée sur les agents, pour le raisonnement contextuel et l'orchestration d'équipes d'assistants IA spécialisés par domaine (B2B).", false));
            achievements1.add(new Achievement("►Développement de trois MVPs pour assistants IA (B2B) basés sur des pipelines RAG, dans les domaines de la construction civile, du coaching pour dirigeants de PME et de la recherche en longévité.", false));
            achievements1.add(new Achievement("►Conception et développement d'un outil pour optimiser le compromis coût-qualité dans l'utilisation des Large Language Models (LLMs).", false));
//            achievements1.add(new Achievement("►Conception et développement d'un composant d'ingestion personnalisé pour données non structurées, incluant les vidéos.", false));
//            oneLineSummary1 = "►Conception d'une plateforme européenne de reconversion et direction technique et produit d'une équipe de 15 experts de plusieurs universités européennes.";
        } else {
            duties1.add(new Duty("►Design and develop AI-native distributed software infrastructure for contextual reasoning and orchestration of AI Assistants."));
//            achievements1.add(new Achievement("►Designed and developed an agent-first AI-native distributed software infrastructure for contextual reasoning and programmable orchestration of teams of domain-specific AI Assistants.", false));
            achievements1.add(new Achievement("►Designed and developed three MVPs for AI assistants (B2B) based on RAG pipelines, in the fields of civil construction, coaching of SME executives and longevity research.", false));
//            achievements1.add(new Achievement("►Designed and developed a tool to optimize the cost-quality trade-off in the usage of Large Language Models (LLMs).", false));
//            achievements1.add(new Achievement("►Designed and developed a custom ingestion component for unstructured data, including videos.", false));
            oneLineSummary1 = "►Built three specialized AI assistants reducing information processing time from hours to minutes in construction compliance, executive coaching, and longevity research.";
        }

        WorkExperience we1 = new WorkExperience("Agenticity.ai", "Jan", 2024, "Present", 2025, "CTO / AI Architect", "Paris, France", 1, achievements1, duties1, isFrench ? "Conseil en IA et IT" : "AI and IT consulting", "", useOneLineSummary1, oneLineSummary1);
        we1.setContractType(translations.get("contract_self_employed"));
        we1.setCompanyContext(isFrench ?
            "Agenticity propose des prestations de conseil en IA et IT, en concevant et en livrant des solutions sur mesure pour des assistants IA spécialisés par domaine et des systèmes multi-agents." :
            "Agenticity provides AI and IT consulting, designing and delivering custom solutions for domain-specialized AI assistants and multi-agent systems.");
        if (isFrench) {
            we1.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Platform, Java, python, Docker, agentic frameworks and \"Al specific” clouds (RunPod, HuggingFace, Mistral Al, Vertex Al, AWS bedrock), protocoles pour les agents (Model Context Protocol, Agent-2-Agent, AG-UI), ChromaDB, RabbitMQ, Nginx, Firebase, websocket, Next.js."));
        } else {
            we1.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Platform, Java, Python, Docker, agent frameworks and protocols (Model Context Protocol, Agent-to-Agent, AG-UI), agentic RAG systems, Al-dedicated cloud services (like RunPod, Hugging Face, AnyScale, Vertex AI), ChromaDB, RabbitMQ, Nginx, Firebase, websocket, Next.js."));
        }

        // Work Experience 2: EU Project D-Reskill
        java.util.List<Duty> duties2 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements2 = new ArrayList<Achievement>();
        String oneLineSummary2 = "";
        boolean useOneLineSummary2 = false;

        if (isFrench) {
            duties2.add(new Duty("►Animation de réunions techniques et études de faisabilité pour plateforme de reconversion."));
            achievements2.add(new Achievement("►Animé des réunions techniques entre partenaires européens (jusqu'à 15 professionnels).", false));
            achievements2.add(new Achievement("►Réalisé des études de faisabilité explorant différentes ontologies et approches pour appliquer l'intelligence des compétences à l'éducation et à la reconversion professionnelle.", false));
            achievements2.add(new Achievement("►Présentations lors de conférences internationales en EdTech.", false));
            oneLineSummary2 = "►Conception d'une plateforme européenne de reconversion et direction technique et produit d'une équipe de 15 experts de plusieurs universités européennes.";
            useOneLineSummary2 = true;
        } else {
            duties2.add(new Duty("►Lead technical meetings and conduct feasibility studies for skill-based reskilling platform."));
            achievements2.add(new Achievement("►Led technical meetings between European partners (15 professionals).", false));
            achievements2.add(new Achievement("►Conducted feasibility studies exploring different ontologies and approaches to apply skill intelligence to education and for reskilling the workforce.", false));
            achievements2.add(new Achievement("►Presented at international EdTech conferences.", false));
            oneLineSummary2 = "►Co-authored successful €300K EU grant proposal and led 15-member consortium across 4 countries to design a reskilling platform with standardized cross-border skills certification.";
            useOneLineSummary2 = true;
        }

        WorkExperience we2 = new WorkExperience("EU Project D-Reskill", "May", 2022, "Nov", 2023, "Consultant CPO/CTO", "Paris, France", 1, achievements2, duties2, isFrench ? "Projet financé par la Commission européenne (Éducation/Reconversion)" : "European Commission Project (Education/Reskilling)", "", useOneLineSummary2, oneLineSummary2);
        we2.setContractType("Freelance");
        we2.setCompanyContext(isFrench ?
            "\"D-Reskill at Universities\" est un projet financé par la Commission européenne visant à concevoir et élaborer une plateforme logicielle pour la reconversion des travailleurs, fondée sur une approche explicite des compétences." :
            "\"D-Reskill at Universities\" is a project funded by the European Commission to envision and design a software platform for reskilling workers using an explicit skill-based approach and ESCO ontology.");
        if (isFrench) {
            we2.setTechnicalEnvironment(new TechnicalEnvironment("Figma, Figma flow, Google Workplace Suite."));
        } else {
            we2.setTechnicalEnvironment(new TechnicalEnvironment("Java, Resource Definition Framework (RDF), SKOS ontologies, Neo4j, Figma, Figma flow."));
        }

        // Work Experience 3: Open University of Catalonia
        java.util.List<Duty> duties3 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements3 = new ArrayList<Achievement>();
        String oneLineSummary3 = "";
        boolean useOneLineSummary3 = false;

        if (isFrench) {
            duties3.add(new Duty("►Conception de plateforme d'orientation professionnelle et direction du développement du prototype."));
            achievements3.add(new Achievement("►Conception d'un produit logiciel dans le domaine de l'employabilité et de l'éducation.", false));
            achievements3.add(new Achievement("►Supervision de deux professionnels dans la réalisation du prototype technique.", false));
            achievements3.add(new Achievement("►Animation d'ateliers de conception à fort impact avec les clients.", false));
            achievements3.add(new Achievement("►Réalisation d'une étude de faisabilité pour l'intégration dans le système informatique universitaire.", false));
            oneLineSummary3 = "►Pilotage de la conception d'un produit EdTech intégré au SI de l'université, gestion de deux experts data et livraison d'un prototype d'aide à la décision, initiant un projet pluriannuel.";
            useOneLineSummary3 = true;
        } else {
            duties3.add(new Duty("►Design career guidance platform and lead prototype development."));
            achievements3.add(new Achievement("►Designed a career guidance platform for the education and employability sectors.", false));
            achievements3.add(new Achievement("►Led a team of 2 professionals in the development of the prototype.", false));
            achievements3.add(new Achievement("►Facilitated high-impact design workshops with clients to drive product ideation and align on strategic goals.", false));
            achievements3.add(new Achievement("►Conducted a feasibility study for integrating the product into the university's IT ecosystem and data pipelines.", false));
            oneLineSummary3 = "►Led the design of an EdTech product integrated with the university information system, managed two data experts, and delivered an operational decision-support prototype, initiating a multi-year project.";
            useOneLineSummary3 = true;
        }

        WorkExperience we3 = new WorkExperience("Open University of Catalonia", "Jun", 2023, "Sep", 2023, "Consultant CPO/CTO", "Barcelona, Spain", 1, achievements3, duties3, isFrench ? "Employabilité et Éducation" : "Education and Employability", "", useOneLineSummary3, oneLineSummary3);
        we3.setContractType("Freelance");
        we3.setCompanyContext(isFrench ?
            "L'UOC est l'une des premières universités européennes entièrement numériques et dispense des cours à des étudiants internationaux depuis plus de 20 ans." :
            "UOC is one of the first European universities fully digital and provides courses to international students for more than 20 years.");
        if (isFrench) {
            we3.setTechnicalEnvironment(new TechnicalEnvironment("Bubble.io, PowerBI, Snowflake, Azure."));
        } else {
            we3.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Run, Power BI, Azure AD, Adobe Cloud (Adobe AEM DXP), Snowflake, Bubble.io, Whimsical."));
        }

        // Work Experience 4: Pixis
        java.util.List<Duty> duties4 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements4 = new ArrayList<Achievement>();
        String oneLineSummary4 = "";
        boolean useOneLineSummary4 = true;

        if (isFrench) {
            duties4.add(new Duty("►Direction de projets complexes basés sur microservices et management d'équipe data science."));
            achievements4.add(new Achievement("►Gestion simultanée d'un maximum de 5 projets complexes pour différents clients.", false));
            achievements4.add(new Achievement("►Management d'un groupe de 5 data scientists dans un projet de machine learning.", false));
            achievements4.add(new Achievement("►Conception et direction de l'implémentation d'un SaaS multi-cloud en marque blanche basé sur microservices.", false));
            achievements4.add(new Achievement("►Développement de preuves de concept techniques devenues éléments clés de nouveaux produits SaaS.", false));
            achievements4.add(new Achievement("►Conception des flux d'autorisation avec Identity Provider (Azure AD B2C).", false));
            oneLineSummary4 = "►Conception de l'architecture logicielle, développement de la solution et direction de l'équipe technique pour la livraison de plateformes SaaS en marque blanche.";
        } else {
            duties4.add(new Duty("►Lead complex projects based on microservices and manage data science team."));
            achievements4.add(new Achievement("►Led concurrently up to 5 complex projects based on microservices.", false));
            achievements4.add(new Achievement("►Managed a group of 5 data scientists in a machine learning project to exploit available data.", false));
            achievements4.add(new Achievement("►Designed and led implementation of a cross-cloud, white-label SaaS, based on microservices.", false));
            achievements4.add(new Achievement("►Developed technical proofs of concept which then became the key elements of new SaaS products.", false));
            achievements4.add(new Achievement("►Designed authorization flows with Identity Provider (Azure AD B2C).", false));
            oneLineSummary4 = "►Designed and presented technical roadmap to investors (contributing to successful fundraising), then led the technical team to deliver white-label SaaS platforms while driving pre-sales, managing key accounts, supported CEO on strategic contract negotiations.";
//            oneLineSummary4 = "►Secured funding from investors and led the technical team to deliver white-label SaaS platforms while driving pre-sales, managing key accounts, and partnering with CEO on strategic contract negotiations.";
        }

        WorkExperience we4 = new WorkExperience("Pixis", "Jul", 2019, "Jul", 2022, "Chief Technology Officer", "Paris, France", 3, achievements4, duties4, isFrench ? "Éducation et Orientation professionnelle" : "Career advice and coaching", "", useOneLineSummary4, oneLineSummary4);
        we4.setCompanyContext(isFrench ?
            "Pixis fournit des solutions SaaS en marque blanche dans le secteur de l'éducation et de l'orientation professionnelle, en utilisant l'ontologie ESCO et les Objectifs de Développement Durable (ODD)." :
            "Pixis provides white-label SaaS solutions in the career advice and coaching sector using ontologies for skills, Sustainable Development Goals and a chatbot that guides the user in interacting with the application.");
        if (isFrench) {
            we4.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Platform, Azure, Java, Python, Docker, Microservices, Github Actions, PostgreSQL, MySQL, OpenAPI, OAuth 2.0, OIDC, JWT, MS Authentication Libraries; Angular 10, Nexus Repository, traditional NLP libraries, semantic-web/RDF, Terraform, Google Analytics, Google Tag Manager, Firebase."));
        } else {
            we4.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Platform, Azure, Java, Python, Docker, Microservices, Github Actions, PostgreSQL, MySQL, OpenAPI, OAuth 2.0, OIDC, JWT, MS Authentication Libraries; Angular 10, Nexus Repository, traditional NLP libraries, semantic-web/RDF, Terraform, Google Analytics, Google Tag Manager, Firebase."));
//            we4.setTechnicalEnvironment(new TechnicalEnvironment("Google Cloud Platform, Azure, Java, Docker, Microservices, Google Colab, Jupyter Notebooks, Python, Github Actions, PostgreSQL, MySQL, Neo4J, OpenAPI, OAuth 2.0, OIDC, JWT, MS Authentication Libraries; Angular 10, Nexus Repository, traditional NLP libraries, semantic-web/RDF, Terraform; Google Analytics, Google Tag Manager, Sketch, Zeplin, Firebase."));
        }

        // Work Experience 5: Cosys
        java.util.List<Duty> duties5 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements5 = new ArrayList<Achievement>();
        String oneLineSummary5 = "";
        boolean useOneLineSummary5 = true;

        if (isFrench) {
            duties5.add(new Duty("►Mise en place de processus agiles et animation des réunions Sprint."));
            achievements5.add(new Achievement("►Mise en place d'un processus agile interne et animation des réunions Sprint.", false));
            achievements5.add(new Achievement("►Création, mise à jour et suivi de la réalisation de la feuille de route.", false));
            oneLineSummary5 = "►Mise en place d'une organisation agile et structuration de la feuille de route et des spécifications produit.";
        } else {
            duties5.add(new Duty("►Introduce agile processes and lead Sprint meetings."));
            achievements5.add(new Achievement("►Introduced an internal agile process and led Sprint meetings.", false));
            achievements5.add(new Achievement("►Created, maintained and facilitated the execution of the Roadmap.", false));
            oneLineSummary5 = "►Implemented an agile organization and structured the product roadmap and product specifications.";
        }

        WorkExperience we5 = new WorkExperience("Cosys", "Mar", 2021, "Feb", 2022, "Scrum Master and Agile Coach", "Paris, France", 1, achievements5, duties5, isFrench ? "SaaS (Construction et gestion d'entreprises à risque partagé)" : "SaaS (Shared-risk company building)", "", useOneLineSummary5, oneLineSummary5);
        we5.setCompanyContext(isFrench ?
            "Le projet Flexup propose un produit SaaS destiné à instaurer un modèle de construction et de gestion d'entreprises basé sur le risque partagé entre les participants." :
            "The Flexup project proposes a SaaS product to implement a model for building and managing companies based on shared risk between participants.");
        we5.setContractType("Freelance");
        if (isFrench) {
            we5.setTechnicalEnvironment(new TechnicalEnvironment("Vue.js, Storybook, Keycloak, Json-server, Github, DigitalOcean droplets, Pulumi, Traefik."));
        } else {
            we5.setTechnicalEnvironment(new TechnicalEnvironment("Vue.js, StoryBook, Keycloak, Traefik, Json-server, DigitalOcean droplets, Pulumi, Vagrant, Confluence."));
        }

        // Work Experience 6: Stealth mode startup
        java.util.List<Duty> duties6 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements6 = new ArrayList<Achievement>();
        String oneLineSummary6 = "";
        boolean useOneLineSummary6 = true;

        if (isFrench) {
            duties6.add(new Duty("►Recueil des besoins, conception de l'architecture et implémentation d'un PoC."));
            achievements6.add(new Achievement("►Recueil des besoins, conception de l'architecture et implémentation d'un PoC de la solution.", false));
            oneLineSummary6 = "►Recueil des besoins, conception de l'architecture et implémentation d'un PoC de la solution.";
        } else {
            duties6.add(new Duty("►Elicit requirements, design architecture and implement PoC for banking sector startup."));
            achievements6.add(new Achievement("►Elicited requirements, designed the architecture and implemented a PoC of the solution.", false));
            oneLineSummary6 = "►Elicited requirements, designed the architecture and implemented a PoC of the solution.";
        }

        WorkExperience we6 = new WorkExperience("Stealth mode startup", "Oct", 2018, "Mar", 2019, "CTO / Software Architect", "Paris, France", 1, achievements6, duties6, isFrench ? "Bancaire" : "Banking", "", useOneLineSummary6, oneLineSummary6);
        we6.setCompanyContext(isFrench ?
            "Participation à la conception d'une startup dans le secteur bancaire." :
            "Participation in the conception of a startup in the banking sector.");
        we6.setTechnicalEnvironment(new TechnicalEnvironment("Google Kubernetes Engine, Minikube, Docker, Java, Spring Boot."));


        // Work Experience 7: Eptica
        java.util.List<Duty> duties7 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements7 = new ArrayList<Achievement>();
        String oneLineSummary7 = "";
        boolean useOneLineSummary7 = true;

        if (isFrench) {
            duties7.add(new Duty("►Amélioration et maintenance d'applications JEE pour centres d'appels multicanal."));
            achievements7.add(new Achievement("►Amélioration et maintenance, au sein d'une équipe SCRUM, d'une suite d'applications JEE représentant environ 80% du chiffre d'affaires.", false));
            achievements7.add(new Achievement("►Conception de l'architecture et preuves de concept pour l'introduction de fonctionnalités linguistiques sur un CRM, comme le routage sémantique des messages.", false));
            achievements7.add(new Achievement("►Direction d'un projet transversal pour améliorer la sécurité des applications selon les recommandations OWASP et RGPD.", false));
            oneLineSummary7 = "►Modernisation, sécurisation et maintien de la continuité opérationnelle d'applications stratégiques représentant environ 80% du chiffre d'affaires.";
        } else {
            duties7.add(new Duty("►Improve and maintain JEE applications for AI-powered multichannel call centers."));
            achievements7.add(new Achievement("►Improved and maintained, within a Scrum team, a suite of interacting JEE applications, that constitutes about 80% of the enterprise revenue.", false));
            achievements7.add(new Achievement("►Designed architecture, user stories and proof of concept for the introduction of linguistic-aware features on a CRM, like the Semantic Routing of messages.", false));
            achievements7.add(new Achievement("►Led a cross-department project to improve security of applications, aligning them with OWASP recommendations and GDPR.", false));
            oneLineSummary7 = "►Modernized and secured mission-critical applications representing ~80% of company revenue, ensuring operational continuity for enterprise clients in banking, insurance, and retail sectors across millions of daily customer interactions.";
//            oneLineSummary7 = "►Modernized, secured, and maintained operational continuity of strategic applications representing approximately 80% of company revenue.";
        }

        WorkExperience we7 = new WorkExperience("Eptica", "Jan", 2016, "Jun", 2018, "Senior Software Engineer", "Boulogne Billancourt, France", 2, achievements7, duties7, isFrench ? "Solutions IA pour centres d'appels multicanal" : "AI-powered solutions for multichannel call centers", "", useOneLineSummary7, oneLineSummary7);
        we7.setCompanyContext(isFrench ?
            "Eptica fournit des solutions propulsées par l'IA pour les centres d'appels multicanal, en exploitant des technologies propriétaires de traitement du langage naturel (NLP) et d'intelligence collaborative." :
            "Eptica provides AI-powered solutions for multichannel call centers exploiting proprietary natural language processing (NLP) technologies and collaborative intelligence.");
        if (isFrench) {
            we7.setTechnicalEnvironment(new TechnicalEnvironment("Java, Spring Boot, Maven, plusieurs frameworks JS, base de données Oracle, PostgreSQL, SQLServer, Docker, Git, Jenkins, ElasticSearch, moteur NLP propriétaire."));
        } else {
            we7.setTechnicalEnvironment(new TechnicalEnvironment("Java, Spring Boot, Docker, JEE, Elasticsearch, PostgreSQL, SQLServer, Oracle DBMS PL/SQL, JBehave, GIT, Jenkins, SonarCube, Gatling, OWASP Zap, Wireshark, TestLink, SquashTM, JIRA, Confluence, proprietary NLP engine."));
//            we7.setTechnicalEnvironment(new TechnicalEnvironment("Java, Spring Boot, Docker, JEE, Elasticsearch, PostgreSQL, SQLServer, Oracle DBMS PL/SQL, JBehave, GIT, Jenkins, SonarCube, Scala/Gatling, OWASP Zap, Wireshark, TestLink, SquashTM, JIRA, Confluence, proprietary NLP engine."));
//            we7.setTechnicalEnvironment(new TechnicalEnvironment("Java, Spring Boot, Docker, JEE, Elasticsearch, PostgreSQL, SQLServer, Oracle DBMS PL/SQL, JBehave, GIT, Jenkins, SonarCube, Scala/Gatling, OWASP zap, Wireshark, TestLink, SquashTM, JIRA, Confluence, proprietary NLP engine, XSLT, jQuery."));
        }


        // Work Experience 8: 2Collaborate Consulting
        java.util.List<Duty> duties8 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements8 = new ArrayList<Achievement>();
        String oneLineSummary8 = "";
        boolean useOneLineSummary8 = false;

        if (isFrench) {
            duties8.add(new Duty("►Conception et développement de preuves de concept pour la méthodologie Action Intelligence."));
            achievements8.add(new Achievement("►Conception et développement de preuves de concept pour implémenter la méthodologie Action Intelligence.", false));
            oneLineSummary8 = "►Collaboration avec le Dr Jerri Husch pour la mise en œuvre de la méthodologie « Action Intelligence ».";
            useOneLineSummary8 = true;
        } else {
            duties8.add(new Duty("►Design and develop proofs of concept for Action Intelligence methodology."));
            achievements8.add(new Achievement("►Designed and developed proofs of concept to implement the Action Intelligence methodology.", false));
            oneLineSummary8 = "►Collaborated with Dr. Jerri Husch on the implementation of the “Action Intelligence” methodology.";
            useOneLineSummary8 = true;
        }

        WorkExperience we8 = new WorkExperience("2Collaborate Consulting", "Sep", 2014, "Sep", 2015, "Business Analyst / Software Architect", "Geneva, Switzerland", 1, achievements8, duties8, isFrench ? "Projets des Nations Unies (Socio-culturel)" : "UN Projects on global themes (Socio-cultural)", "", useOneLineSummary8, oneLineSummary8);
        we8.setCompanyContext(isFrench ?
            "2Collaborate travaille dans des contextes socioculturels complexes tels que les projets des Nations Unies sur des thèmes mondiaux, en promouvant une méthodologie innovante appelée \"Action Intelligence\"." :
            "2Collaborate works in complex socio-cultural contexts such as United Nations projects on global themes, promoting an innovative methodology called \"Action Intelligence\".");
        we8.setTechnicalEnvironment(new TechnicalEnvironment("IBM Watson NLU, Heroku, World Wind, Apache POI, graph libraries (Jung, Gephi, Ubigraph, Sigmajs), Java."));


        // Work Experience 9: Terabee
        java.util.List<Duty> duties9 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements9 = new ArrayList<Achievement>();
        String oneLineSummary9 = "";
        boolean useOneLineSummary9 = false;

        if (isFrench) {
            duties9.add(new Duty("►Direction d'équipe R&D pour projets drones et capteurs en collaboration avec le CERN."));
            achievements9.add(new Achievement("►Coordination d'une équipe de 4 personnes dans un projet impliquant smartphones et drones dans le secteur sportif.", false));
            achievements9.add(new Achievement("►Mise en œuvre d'un ensemble de services de photogrammétrie ayant permis à l'entreprise d'initier de nouvelles lignes d'activité.", false));
            oneLineSummary9 = "►Direction de projets R&D ayant permis le lancement de nouveaux services de photogrammétrie 3D.";
            useOneLineSummary9 = true;
        } else {
            duties9.add(new Duty("►Lead R&D team for drone and sensor projects in collaboration with CERN."));
            achievements9.add(new Achievement("►Led a 4-member team in a project that involved smartphones and drones in the golf sector.", false));
            achievements9.add(new Achievement("►Implemented a set of photogrammetry services (like 3D reconstructions and orthomosaics) which enabled the company to explore new business opportunities related to aerial inspection.", false));
            oneLineSummary9 = "►Led R&D projects that enabled the launch of new aerial inspection services.";
            useOneLineSummary9 = true;
        }

        WorkExperience we9 = new WorkExperience("Terabee", "Mar", 2013, "Aug", 2014, "R&D Software Lead", "St. Genis Pouilly, France", 1, achievements9, duties9, isFrench ? "Drones et capteurs innovants" : "Drones and advanced sensors", "", useOneLineSummary9, oneLineSummary9);
        we9.setCompanyContext(isFrench ?
            "Terabee collabore avec le CERN dans le domaine des drones et des capteurs innovants." :
            "Terabee collaborates with CERN in the market of drones and advanced sensors.");
        if (isFrench) {
            we9.setTechnicalEnvironment(new TechnicalEnvironment("Java, Python, outils et bibliothèques pour la manipulation de nuages de points, de maillages, de données de photogrammétrie ainsi que de données géographiques et géospatiales, Real-Time Kinematics, ainsi que des capteurs avancés à temps de vol, Precision GPS."));
            //
            //Outils et bibliothèques pour la manipulation de nuages de points, de maillages, de données de photogrammétrie ainsi que de données géographiques et géospatiales, incluant des outils tels que Pix4D, Photoscan, ArcGIS et Global Mapper, ainsi que des capteurs avancés à temps de vol (Time-of-Flight).
            //Outils liés aux drones, notamment les planificateurs de mission.
        } else {
            we9.setTechnicalEnvironment(new TechnicalEnvironment("Java, Python, tools and libraries for manipulation of point clouds, meshes, photogrammetry, geographical and geospatial data, Time-of-flight sensors. Precision GPS. Mission planners for drones."));
//            we9.setTechnicalEnvironment(new TechnicalEnvironment("Java, Python, tools and libraries for manipulation of point clouds, meshes, photogrammetry, geographical and geospatial data, Advanced time-of-flight sensors. Precision GPS, Real-Time Kinematics. Drones related tools (mission planners)."));
            //GeoTools, JSSC/java-comm, JFreeChart, Jama, GDAL.
            //            we9.setTechnicalEnvironment(new TechnicalEnvironment("Java, Python, GeoTools, JSSC/java-comm, JFreeChart, Jama, GDAL. Drones related tools (mission planners). Tools and libraries for manipulation of point-clouds, meshes, photogrammetry, geographical and geospatial data, including tools like Pix4D, Photoscan, ArcGIS, advanced time-of-flight sensors."));
        }

        // Work Experience 10: italianaSoftware
        java.util.List<Duty> duties10 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements10 = new ArrayList<Achievement>();
        String oneLineSummary10 = "";
        boolean useOneLineSummary10 = false;

        if (isFrench) {
            duties10.add(new Duty("►Gestion de projet et direction d'équipe pour projets IT."));
            duties10.add(new Duty("►Conception, analyse et développement d'applications web orientées services."));
            achievements10.add(new Achievement("►Direction de 4 professionnels dans un projet de 5 mois pour remplacer une plateforme e-commerce B2B.", false));
            achievements10.add(new Achievement("►Conception et implémentation d'un synchroniseur de données temps réel pour un e-commerce distribué.", false));
            achievements10.add(new Achievement("►Réalisation de missions de conseil externe en tant qu'ingénieur QA dans l'industrie des télécoms mobiles.", false));
            achievements10.add(new Achievement("►Direction d'un projet d'intégration de microservices dans une plateforme de développement web.", false));
            oneLineSummary10 = "►Direction technique de projets et d'équipes informatiques représentant 70% du chiffre d'affaires de l'entreprise.";
            useOneLineSummary10 = true;
        } else {
            duties10.add(new Duty("►Project management and team leadership of IT projects."));
            duties10.add(new Duty("►Conception, analysis, design and development of innovative service-oriented web applications."));
            achievements10.add(new Achievement("►Led a team of 4 professionals in a 5-month project to replace a B2B eCommerce platform in the industrial metal components sector.", false));
            achievements10.add(new Achievement("►Designed and implemented a fail-safe real-time data synchronizer for a distributed eCommerce of office supplies.", false));
            achievements10.add(new Achievement("►Accomplished an external consultancy as QA engineer in the mobile carrier industry, in a system with more than 500 services.", false));
            achievements10.add(new Achievement("►Led a project for the integration of microservices within a platform for web app development.", false));
            oneLineSummary10 = "►Technical leadership of IT projects and teams representing approximatively 75% of company revenue.";
            useOneLineSummary10 = true;
        }

        WorkExperience we10 = new WorkExperience("italianaSoftware", "Sep", 2010, "Nov", 2012, "Software Architect / Project Manager", "Imola, Italy", 2, achievements10, duties10, isFrench ? "Microservices et intégration de systèmes" : "Microservices and system integration", "", useOneLineSummary10, oneLineSummary10);
        we10.setCompanyContext(isFrench ?
            "ItalianaSoftware est spécialisée dans les microservices et l'intégration de systèmes; l'entreprise collabore avec des centres d'excellence (INRIA, SDU) et une communauté internationale." :
            "ItalianaSoftware is specialized in microservices and systems integration. They collaborate with centers of excellence (INRIA, SDU) and an international community.");
        if (isFrench) {
            we10.setTechnicalEnvironment(new TechnicalEnvironment("Java ecosystem, Maven, Postgresql, Github, Microsoft Dynamics, Aqualogic Service Bus."));
        } else {
            we10.setTechnicalEnvironment(new TechnicalEnvironment("Java, Nexus artifact repository, Web Services, SOAP, MS Dynamics, SQLServer, Tomcat, GWT, Swing, SoapUI, PostgreSQL, Jolie-lang, custom API Gateway, Enterprise Service Buses, EDI Standards."));
        }

        // Work Experience 11: GemBB
        java.util.List<Duty> duties11 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements11 = new ArrayList<Achievement>();
        String oneLineSummary11 = "";
        boolean useOneLineSummary11 = false;

        if (isFrench) {
            duties11.add(new Duty("►Supervision de la construction d'applications ETL intégrant des systèmes existants."));
            achievements11.add(new Achievement("►Supervision de la mise en place de 3 applications Extract-Transform-Load intégrant des systèmes existants.", false));
            oneLineSummary11 = "►Contribution clé à l'innovation stratégique par la conception et gestion de projets ETL et connecteurs de systèmes hérités, et formation de développeur.";
            useOneLineSummary11 = true;
        } else {
            duties11.add(new Duty("►Supervise construction of ETL applications involving legacy systems."));
            achievements11.add(new Achievement("►Supervised the construction of 3 Extract-Transform-Load applications involving legacy systems.", false));
            oneLineSummary11 = "►Key contribution to the company's strategic innovation through the design and management of ETL projects and legacy system connectors, along with training a developer.";
            useOneLineSummary11 = true;
        }

        WorkExperience we11 = new WorkExperience("GemBB", "Jun", 2011, "Jun", 2012, "Consultant / Resource Trainer", "San Marino", 1, achievements11, duties11, isFrench ? "Sécurité au travail" : "Workplace safety", "", useOneLineSummary11, oneLineSummary11);
        we11.setCompanyContext(isFrench ?
            "GemBB est actif dans le secteur de la sécurité au travail et intègre des systèmes existants." :
            "GemBB provides technical consulting and compliance services in workplace and industrial machinery safety.");
        we11.setContractType("Freelance");
        if (isFrench) {
            we11.setTechnicalEnvironment(new TechnicalEnvironment("Java ecosystem, Maven, bibliothèques pour IBM AS400 et l'interopérabilité java-COM."));
        } else {
            we11.setTechnicalEnvironment(new TechnicalEnvironment("Microsoft COM/Jacob libraries, JTOpen, AS400, Java, Swing."));
        }

        // Work Experience 12: Gheotech
        java.util.List<Duty> duties12 = new ArrayList<Duty>();
        java.util.List<Achievement> achievements12 = new ArrayList<Achievement>();
        String oneLineSummary12 = "";
        boolean useOneLineSummary12 = false;

        if (isFrench) {
            duties12.add(new Duty("►Gestion, développement, maintenance, déploiement et support de projets JEE."));
            duties12.add(new Duty("►Gestion des relations avec les parties prenantes des projets."));
            achievements12.add(new Achievement("►Direction de 6 projets, coordination de 3 professionnels et interaction avec les clients/revendeurs.", false));
            achievements12.add(new Achievement("►Amélioration majeure des performances d'un serveur de vidéosurveillance, doublant le nombre de flux temps réel, les types de caméras supportés et la precision du dectection du moviment, conduisant à l’acquisition de nouveaux contrats clients.", false));
            oneLineSummary12 = "►Gestion simultanée de 6 projets techniques et refonte d'un serveur de vidéosurveillance ayant doublé les performances et permis la signature de nouveaux contrats majeurs.";
            useOneLineSummary12 = true;
        } else {
            duties12.add(new Duty("►Management, development, maintenance, deployment and support of JEE projects."));
            duties12.add(new Duty("►Management of the relationships with the stakeholders of the projects."));
            achievements12.add(new Achievement("►Led up to 6 projects, coordinating 3 professionals and interacting with stakeholders.", false));
            achievements12.add(new Achievement("►Highly improved the performance of a video surveillance server in terms of real-time streams handling, supported camera types and the precision of movement detection, leading to the acquisition of more client contracts.", false));
            oneLineSummary12 = "►Simultaneous management of 6 technical projects, major upgrade of a video surveillance server that doubled performance and enabled the signing of new key contracts.";
            useOneLineSummary12 = true;
        }

        WorkExperience we12 = new WorkExperience("Gheotech", "Oct", 2009, "Aug", 2010, "Analyst / Developer", "Cattolica, Italy", 1, achievements12, duties12, isFrench ? "Gestion des infrastructures urbaines (GIS)" : "Public utility distribution (GIS)", "", useOneLineSummary12, oneLineSummary12);
        we12.setCompanyContext(isFrench ?
            "Gheotech intervient dans la gestion des infrastructures urbaines (distribution d'eau, de gaz et de fibre)." :
            "Gheotech operates in the field of public utility distribution (water, optical fiber, gas), leveraging its proprietary Geographic Information System (GIS), distributed documentation systems and video surveillance server.");
        if (isFrench) {
            we12.setTechnicalEnvironment(new TechnicalEnvironment("Java ecosystem, EJB, JBPM, PostgreSQL, bibliothèque pour vidéo-surveillance."));
        } else {
            we12.setTechnicalEnvironment(new TechnicalEnvironment("Java ecosystem, WebServices, PostgreSQL, Rackspace, VirtualBox, PostGIS. Video streaming libs tools and protocols (VLC, Red5, ffmpeg), various types of IP cameras."));
//            we12.setTechnicalEnvironment(new TechnicalEnvironment("JBoss, EJB3, jBPM, Tomcat, JSF, JPA/Hibernate, JAX-WS, PostgreSQL, GWT/GXT, dojo toolkit, Batik, JasperReports, BIRT, Rackspace, VirtualBox, PostGIS. Video streaming libs tools and protocols (VLC, Red5, ffmpeg), various types of IP cameras."));
        }

        // Add all work experiences
        workExperiences.add(we1);
        workExperiences.add(we2);
        workExperiences.add(we3);
        workExperiences.add(we4);
        workExperiences.add(we5);
        workExperiences.add(we6);
        workExperiences.add(we7);
        workExperiences.add(we8);
        workExperiences.add(we9);
        workExperiences.add(we10);
        workExperiences.add(we11);
        workExperiences.add(we12);

        Paragraph parExperiences = new Paragraph();

        for (WorkExperience work_experience : workExperiences) {
            // Date formatting
            String startMonth = work_experience.getStartMonth() != null ? translations.get(work_experience.getStartMonth()) : "";
            String endMonth = work_experience.getEndMonth() != null ? translations.get(work_experience.getEndMonth()) : "";
            String startDate = (startMonth.isEmpty() ? "" : startMonth + " ") + work_experience.getStartYear();
            String endDate;
            if ("Present".equals(work_experience.getEndMonth())) {
                endDate = translations.get("Present");
            } else {
                endDate = (endMonth.isEmpty() ? "" : endMonth + " ") + work_experience.getEndYear();
            }

            // Work experience extras.header (date + role + company + location + contract type)
            Paragraph expHeader = new Paragraph();
            Chunk headerChunk = new Chunk(startDate + " – " + endDate + "  ", font_bold_10);
            String companyLocation = work_experience.getCompanyName();
            if (work_experience.getLocation() != null && !work_experience.getLocation().isEmpty()) {
                companyLocation += " (" + work_experience.getLocation();
                // Add contract type if present
                if (work_experience.getContractType() != null && !work_experience.getContractType().isEmpty()) {
                    companyLocation += ") - " + work_experience.getContractType();
                } else {
                    companyLocation += ")";
                }
            } else if (work_experience.getContractType() != null && !work_experience.getContractType().isEmpty()) {
                // Contract type without location
                companyLocation += " - " + work_experience.getContractType();
            }
            Chunk roleChunk = new Chunk(work_experience.getRole() + " - " + companyLocation, font_bold_10);
            expHeader.add(headerChunk);
            expHeader.add(roleChunk);
            expHeader.setSpacingBefore(8);
            parExperiences.add(expHeader);

            // Company Business Sector
            if (DISPLAY_BUSINESS_SECTOR_ENABLED && work_experience.getCompanyBusinessSector() != null && !work_experience.getCompanyBusinessSector().isEmpty()) {
                Paragraph companyBusinessSectorPar = new Paragraph(work_experience.getCompanyBusinessSector(), font_10);
                companyBusinessSectorPar.setIndentationLeft(10);
                //companyBusinessSectorPar.setSpacingAfter(3);
                parExperiences.add(companyBusinessSectorPar);
            }

            // Company description (context)
            if (work_experience.getCompanyContext() != null && !work_experience.getCompanyContext().isEmpty()) {
                Paragraph companyContext = new Paragraph();
                companyContext.setIndentationLeft(10);
                companyContext.setSpacingAfter(3);

                // Check if the text contains "INRIA" or "SDU" and make them clickable
                String contextText = work_experience.getCompanyContext();
                if (contextText.contains("INRIA") || contextText.contains("SDU")) {
                    int lastIndex = 0;
                    int index;

                    while (lastIndex < contextText.length()) {
                        int inriaIndex = contextText.indexOf("INRIA", lastIndex);
                        int sduIndex = contextText.indexOf("SDU", lastIndex);

                        // Find which keyword comes first
                        if (inriaIndex != -1 && (sduIndex == -1 || inriaIndex < sduIndex)) {
                            // Add text before INRIA
                            if (inriaIndex > lastIndex) {
                                companyContext.add(new Chunk(contextText.substring(lastIndex, inriaIndex), font_10));
                            }
                            // Add INRIA as link
                            Chunk inriaChunk = new Chunk("INRIA", linkFont);
                            inriaChunk.setAnchor("https://www.inria.fr");
                            companyContext.add(inriaChunk);
                            lastIndex = inriaIndex + 5; // length of "INRIA"
                        } else if (sduIndex != -1) {
                            // Add text before SDU
                            if (sduIndex > lastIndex) {
                                companyContext.add(new Chunk(contextText.substring(lastIndex, sduIndex), font_10));
                            }
                            // Add SDU as link
                            Chunk sduChunk = new Chunk("SDU", linkFont);
                            sduChunk.setAnchor("https://www.sdu.dk");
                            companyContext.add(sduChunk);
                            lastIndex = sduIndex + 3; // length of "SDU"
                        } else {
                            // No more keywords, add remaining text
                            companyContext.add(new Chunk(contextText.substring(lastIndex), font_10));
                            break;
                        }
                    }
                } else {
                    // No INRIA or SDU in the text, add as normal
                    companyContext.add(new Chunk(contextText, font_10));
                }

                parExperiences.add(companyContext);
            }

            // Duties
            if (DUTIES_ENABLED && work_experience.getDuties() != null && !work_experience.getDuties().isEmpty()) {
                for (Duty duty : work_experience.getDuties()) {
                    Paragraph dutyPar = new Paragraph(duty.toString(), font_10);
                    dutyPar.setIndentationLeft(10);
                    parExperiences.add(dutyPar);
                }
            }

            // Achievements
            if (work_experience.isOneLineAchievementEnabled() && work_experience.getOneLineAchievement() != null && !work_experience.getOneLineAchievement().isEmpty()) {
                Paragraph achPar = new Paragraph(work_experience.getOneLineAchievement(), font_10);
                achPar.setIndentationLeft(10);
                parExperiences.add(achPar);
            } else if (work_experience.getAchievements() != null && !work_experience.getAchievements().isEmpty()) {
                for (Achievement achievement : work_experience.getAchievements()) {
                    Paragraph achPar = new Paragraph(achievement.toString(), font_10);
                    achPar.setIndentationLeft(10);
                    parExperiences.add(achPar);
                }
            }

            // Technical Environment
            if (TECH_ENV_SECTION_ENABLED && work_experience.getTechnicalEnvironment() != null && !work_experience.getTechnicalEnvironment().isEmpty()) {
                TechnicalEnvironment techEnv = work_experience.getTechnicalEnvironment();
                Font font_blue_10 = FontFactory.getFont("Arial", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10, Font.NORMAL, BaseColor.BLUE);

                if (techEnv.isOneLine()) {
                    Paragraph techEnvPar = new Paragraph();
                    techEnvPar.setIndentationLeft(10);
                    techEnvPar.setSpacingBefore(5);
                    Chunk techEnvTitle = new Chunk(translations.get("tech_env_title") + ": ", font_blue_10);
                    techEnvPar.add(techEnvTitle);

                    // Check if the text contains "Jolie-lang" or "JOLIE" and make them clickable
                    String techEnvText = techEnv.getOneLineText();
                    if (techEnvText.contains("Jolie-lang") || techEnvText.contains("JOLIE")) {
                        int lastIndex = 0;

                        while (lastIndex < techEnvText.length()) {
                            int jolieLangIndex = techEnvText.indexOf("Jolie-lang", lastIndex);
                            int jolieIndex = techEnvText.indexOf("JOLIE", lastIndex);

                            // Find which keyword comes first (prefer "Jolie-lang" over "JOLIE" if at same position)
                            if (jolieLangIndex != -1 && (jolieIndex == -1 || jolieLangIndex <= jolieIndex)) {
                                // Add text before Jolie-lang
                                if (jolieLangIndex > lastIndex) {
                                    techEnvPar.add(new Chunk(techEnvText.substring(lastIndex, jolieLangIndex), font_10));
                                }
                                // Add Jolie-lang as link
                                Chunk jolieChunk = new Chunk("Jolie-lang", linkFont);
                                jolieChunk.setAnchor("https://www.jolie-lang.org/");
                                techEnvPar.add(jolieChunk);
                                lastIndex = jolieLangIndex + 10; // length of "Jolie-lang"
                            } else if (jolieIndex != -1) {
                                // Add text before JOLIE
                                if (jolieIndex > lastIndex) {
                                    techEnvPar.add(new Chunk(techEnvText.substring(lastIndex, jolieIndex), font_10));
                                }
                                // Add JOLIE as link
                                Chunk jolieChunk = new Chunk("JOLIE", linkFont);
                                jolieChunk.setAnchor("https://www.jolie-lang.org/");
                                techEnvPar.add(jolieChunk);
                                lastIndex = jolieIndex + 5; // length of "JOLIE"
                            } else {
                                // No more keywords, add remaining text
                                techEnvPar.add(new Chunk(techEnvText.substring(lastIndex), font_10));
                                break;
                            }
                        }
                    } else {
                        techEnvPar.add(new Chunk(techEnvText, font_10));
                    }

                    parExperiences.add(techEnvPar);
                } else {
                    // Title "Technical Environment" in blue (no bold)
                    Paragraph techEnvTitle = new Paragraph(translations.get("tech_env_title") + ":", font_blue_10);
                    techEnvTitle.setIndentationLeft(10);
                    techEnvTitle.setSpacingBefore(5);
                    parExperiences.add(techEnvTitle);

                    // Categories
                    for (TechnicalEnvironmentCategory category : techEnv.getCategories()) {
                        Paragraph categoryPar = new Paragraph();
                        categoryPar.setIndentationLeft(10);

                        // Category title in blue (no bold)
                        Chunk categoryTitle = new Chunk(translations.get(category.getTitle()) + ": ", font_blue_10);
                        categoryPar.add(categoryTitle);

                        // Technologies in normal font
                        String techList = String.join(", ", category.getTechnologies());
                        Chunk technologies = new Chunk(techList + ".", font_10);
                        categoryPar.add(technologies);

                        parExperiences.add(categoryPar);
                    }
                }
            }
        }

        Paragraph par3_title = new Paragraph(translations.get("experience_title"), font_bold_12);
        applyTitleStyle(par3_title);

        // Education & Qualifications
        Paragraph par4_title = new Paragraph(translations.get("education_title") + "\n", font_bold_12);
        applyTitleStyle(par4_title);
        Paragraph par4 = new Paragraph();
        Phrase phr4_1 = new Phrase();
        Chunk chunk4_1 = new Chunk(translations.get("education_1"), font_10);
        Chunk chunk4_2 = new Chunk(translations.get("education_2"), font_10);
        Chunk chunk4_3 = new Chunk(translations.get("education_3"), font_10);

        phr4_1.add(chunk4_1);
        phr4_1.add(Chunk.NEWLINE);
        phr4_1.add(chunk4_2);
        phr4_1.add(Chunk.NEWLINE);
        phr4_1.add(chunk4_3);
        phr4_1.setFont(font_10);
        par4.add(phr4_1);

        // Languages
//        Paragraph par5_title = new Paragraph(translations.get("languages_title") + "\n", font_bold_12);
//        applyTitleStyle(par5_title);
//        Paragraph par5 = new Paragraph();
//
//        Phrase phr5_1 = new Phrase();
//        Chunk chunk5_1 = new Chunk(translations.get("lang_italian"));
//        Chunk chunk5_2 = new Chunk(translations.get("lang_english"));
//        Chunk chunk5_3 = new Chunk(translations.get("lang_french"));
//        phr5_1.add(chunk5_1);
//        phr5_1.add(chunk5_2);
//        phr5_1.add(chunk5_3);
//        phr5_1.setFont(font_10);
//        par5.add(phr5_1);

        // Build document with bookmarks
        // Main title
        document.add(par1_title);
        if (INTRO_ENABLED) {
            document.add(par1_intro);
        }

        // Profile section with bookmark
        if (PROFILE_ENABLED) {
            new PdfOutline(root, new PdfDestination(PdfDestination.FITH, writer.getVerticalPosition(true)), translations.get("profile_title"), false);
            document.add(par2_title);
            document.add(par2_profile);
        }

        // Key Competencies section with bookmark
        if (COMPETENCE_SECTION_ENABLED) {
            new PdfOutline(root, new PdfDestination(PdfDestination.FITH, writer.getVerticalPosition(true)), translations.get("competencies_title"), false);
            document.add(par_comp_title);
            document.add(par_competencies);
        }

        // Key Professional Experience section with bookmark
        new PdfOutline(root, new PdfDestination(PdfDestination.FITH, writer.getVerticalPosition(true)), translations.get("experience_title"), false);
        document.add(par3_title);
        document.add(parExperiences);

        // Education & Qualifications section with bookmark
        new PdfOutline(root, new PdfDestination(PdfDestination.FITH, writer.getVerticalPosition(true)), translations.get("education_title"), false);
        document.add(par4_title);
        document.add(par4);

        // Languages section with bookmark
//        new PdfOutline(root, new PdfDestination(PdfDestination.FITH, writer.getVerticalPosition(true)), translations.get("languages_title"), false);
//        document.add(par5_title);
//        document.add(par5);

        int numberOfPages = writer.getPageNumber();
        document.close();
        writer.close();

        System.out.println("PDF generated: " + filename);
        System.out.println("Number of pages: " + numberOfPages);
    }

    public static void applyTitleStyle(Paragraph par1_title) {
        par1_title.setSpacingBefore(SPACING_BEFORE);
        par1_title.setSpacingAfter(SPACING_AFTER);
        par1_title.setAlignment(Element.ALIGN_CENTER);
        par1_title.setIndentationLeft(50);
        par1_title.setIndentationRight(50);
        par1_title.setFont(font_bold_12);
    }

    public static void applyStyleText(Paragraph par) {
        par.setAlignment(Element.ALIGN_LEFT);
        par.setAlignment(Element.ALIGN_JUSTIFIED);
        par.setIndentationLeft(50);
        par.setIndentationRight(50);
        par.setFont(font_10);
    }

    public static void main(String[] args) throws DocumentException, IOException, SQLException {
        CV_Generator=new CV_java_to_PDF_Generator();
        //Configuration
        CV_FILENAME="CV_BULLINI_" +"CTO_"+ "EN" + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".pdf";
        CV_Generator.createPdf(CV_FILENAME, "EN");
        CV_FILENAME="CV_BULLINI_" +"CTO_"+ "FR" + "_" + new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + ".pdf";
        CV_Generator.createPdf(CV_FILENAME, "FR");
    }
}
