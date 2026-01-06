package ai.agenticity.cv.generator.domain;

import java.util.HashMap;
import java.util.Map;

import static ai.agenticity.cv.generator.CV_java_to_PDF_Generator.TARGET_COMPANY_IS_A_STARTUP;
import static ai.agenticity.cv.generator.CV_java_to_PDF_Generator.TARGET_SECTOR_IS_EDUCATION_OR_EMPLOYABILITY_OR_SKILLS;

/**
 * Translations for CV content in different languages
 */
public class CVTranslations {

    private String language;
    private Map<String, String> translations;

    public CVTranslations(String language) {
        this.language = language;
        this.translations = new HashMap<>();
        loadTranslations();
    }

    private void loadTranslations() {
        if ("FR".equals(language)) {
            loadFrenchTranslations();
        } else {
            loadEnglishTranslations();
        }
    }

    private void loadEnglishTranslations() {
        // Titles
        translations.put("main_title", "CHIEF TECHNOLOGY OFFICER - AI EXPERT");
        translations.put("profile_title", "Profile");
        translations.put("competencies_title", "Key Competencies");
        translations.put("experience_title", "Key Professional Experience");
        translations.put("education_title", "Education & Qualifications");
        translations.put("languages_title", "Languages");

        // Introduction
        translations.put("intro_1", "7 years as Chief Technology Officer and leader of R&D projects in SME and startups.\n");
        if (TARGET_COMPANY_IS_A_STARTUP) {
            translations.put("intro_2", "4 startups built from scratch, contributing as CTO and minoritary partner.\n");
        }
        translations.put("intro_3", "16 years of professional experience designing, developing and delivering distributed systems and SaaS platforms.\n");
//        translations.put("intro_3", "16 years of professional experience designing, developing and delivering distributed systems and SaaS platforms. " +
//                "**Expert in Agentic AI, Multi-Agent Systems and Generative AI.**\n");
        translations.put("intro_4", "12 years of international experience delivering high-impact outcomes within agile, multicultural teams across multiple countries.\n");
//        translations.put("intro_4", "12 years of practice working in agile teams, acting as both Senior Engineer and Scrum Master.\n");
//        translations.put("intro_5", "12 years of international experience working in culturally diverse contexts across several countries.\n");
        String txtBaseEN="10+ application domains covered throughout my career";
        String textFinalEN=txtBaseEN;
        if (TARGET_SECTOR_IS_EDUCATION_OR_EMPLOYABILITY_OR_SKILLS) {
            textFinalEN=txtBaseEN+", including 5 years in EdTech, Reskilling and Upskilling.";}
        else {
            textFinalEN=txtBaseEN+".";
        }
        translations.put("intro_6",textFinalEN+"\n");

        translations.put("intro_7", "3 languages spoken with full professional proficiency: French, English, and Italian.\n");
        translations.put("intro_8", "6 years as independent consultant and Fractional CTO and CPO.\n");
        translations.put("intro_9", "6 years of management experience, leading teams of up to 15 professionals.\n");
        if (TARGET_COMPANY_IS_A_STARTUP) {
            translations.put("intro_10", "10 years within startup ecosystems, spanning incubators and accelerators such as Innovami, CERN Technoparc, Station F, and Pépinière 26.\n");
        }
        // Profile
        translations.put("profile_1", "Result-driven engineering leader combining strategic vision in AI and cybersecurity with operational execution. Expert in Agentic AI, Multi-Agent Systems and Adoption of AI.\n");
//        translations.put("profile_1", "Intellectually curious, quick learner and strategic team leader, with a strong focus on AI and security.\n");
//        translations.put("profile_2", "From university labs with multi-agent systems and agentic AI, to production-grade AI technologies.\n");
        translations.put("profile_2", "Leveraging latest research to deliver production-grade AI systems.\n");
//        translations.put("profile_2", "Bridged academic research in multi-agent systems and agentic AI with delivery of production-grade AI technologies.\n");
//        translations.put("profile_2", "My journey began in university labs with multi-agent systems and agentic AI, and has evolved into solid expertise in innovation, industry standards, and best practices, culminating in recent experience with cutting-edge AI technologies.\n");
        translations.put("profile_3", "Career progression from engineer through architect to CTO.\n");
//        translations.put("profile_3", "Worked in SMEs and gave structure to startups, moving from developer to architect and ultimately into strategic leadership roles as a CTO, with a hands-on, operational approach.\n");
//        translations.put("profile_3", "Along the way, I progressed across SMEs and startups, moving from developer to architect and ultimately into strategic leadership roles as a CTO, while always maintaining a hands-on, operational approach.\n");
//        translations.put("profile_4", "Experienced in hybrid and remote collaboration.\n"); //, thriving in international environments

        // Competencies titles
        translations.put("comp_mgmt_title", "Management of AI and Software engineering projects:\n");
        translations.put("comp_ai_title", "AI technologies:\n");
        translations.put("comp_tech_title", "Other Technologies:\n");
        translations.put("comp_arch_title", "Software architecture:\n");
        translations.put("comp_cloud_ia_title", "AI Cloud Platforms:\n");
        translations.put("comp_cloud_title", "Cloud:\n");
        translations.put("comp_front_title", "Front-end:\n");
        translations.put("comp_sec_title", "Security and privacy:\n");
        translations.put("comp_dev_title", "Development practices:\n");

        // Competencies content
        translations.put("comp_mgmt", "R&D Management, Lead of complex projects, Adoption of AI, Agentic Systems, custom Retrieval-Augmented Generation pipelines (RAG), local AI assistants, Multi-Agent Systems, Context Engineering.\n\n");
        translations.put("comp_ai", "LLM-related libraries (LangChain, LiteLLM, RouteLLM, Lark, FastAPI, mem0.ai), Ollama, vLLM, Docker-MCP, Model Context Protocol (MCP), agent-to-agent protocol (A2A), AG-UI protocol, Gradio, Streamlit, CoPilotKit, Vector Databases.\n\n");
        translations.put("comp_tech", "Python, Java, Spring Boot, JEE, API implementation, API Managers, Nginx, SQL, Relational Databases, NoSQL, MongoDB, Elasticsearch, Redis, Neo4J, RabbitMQ, Kafka, n8n.\n\n");
        translations.put("comp_arch", "Microservice architectural style, Domain Driven Design, Agentic AI Architectures, SOA (Service Oriented Architectures), Enterprise Integration Patterns, MVC Frameworks.\n\n");
        translations.put("comp_cloud_ia", "Google Vertex AI, RunPod, AnyScale, AWS Bedrock.\n\n");
        translations.put("comp_cloud", "Google Cloud Platform, Heroku, Netlify, Vercel, OVHCloud, Azure, AWS (S3, EC2), Scaleway, Docker, Docker Compose, Google Kubernetes Engine, CI/CD pipelines, Observability tools (Google Cloud Monitoring etc).\n\n");
        translations.put("comp_front", "Angular, Next.js.\n\n");
        translations.put("comp_sec", "OAuth 2.0, OIDC, JWT, Identity Providers, implementation of OWASP recommendations and GDPR compliance, ISO/IEC-27001.\n\n");
        translations.put("comp_dev", "Test-Driven Development (TDD), Behavior-Driven Development (BDD), design, architectural, and agentic patterns.\n");

        // Education
        translations.put("education_1", "Professionally qualified as a Chartered Engineer in Italy.");
        translations.put("education_2", "Master's Degree in Computer Engineering, Bologna University, Italy.");
        translations.put("education_3", "Specialization: Systems and Applications.");

        // Languages
        translations.put("lang_italian", "Italian: bilingual   ");
        translations.put("lang_english", "English: professional proficiency   ");
        translations.put("lang_french", "French: professional proficiency");

        // Months
        translations.put("Jan", "Jan");
        translations.put("Feb", "Feb");
        translations.put("Mar", "Mar");
        translations.put("Apr", "Apr");
        translations.put("May", "May");
        translations.put("Jun", "Jun");
        translations.put("Jul", "Jul");
        translations.put("Aug", "Aug");
        translations.put("Sep", "Sep");
        translations.put("Oct", "Oct");
        translations.put("Nov", "Nov");
        translations.put("Dec", "Dec");
        translations.put("Present", "Present");
        translations.put("contract_self_employed", "Independent consultant");

        // PDF metadata
        translations.put("pdf_title", "Francesco Bullini - CV");
        translations.put("see_details", " (see details)");
        translations.put("go_to_original", "Go to original document");

        // Technical Environment
        translations.put("tech_env_title", "Technical Environment");
        translations.put("tech_env_languages", "Languages");
        translations.put("tech_env_backend_framework", "Backend Framework");
        translations.put("tech_env_llm_libraries", "LLM libraries");
        translations.put("tech_env_llm_providers", "LLM Providers and API");
        translations.put("tech_env_protocols", "Protocols");
        translations.put("tech_env_cloud", "Cloud");
        translations.put("tech_env_middleware_db", "Middleware and Databases");
        translations.put("tech_env_security", "Security");
        translations.put("tech_env_databases", "Databases");
        translations.put("tech_env_semantic_web", "Semantic Web/Ontologies");
        translations.put("tech_env_ux_ui", "UX/UI");
        translations.put("tech_env_authentication", "Authentication");
        translations.put("tech_env_ci_cd", "CI/CD");
        translations.put("tech_env_nlp_ml", "NLP / ML");
        translations.put("tech_env_no_code", "No-Code");
        translations.put("tech_env_other", "Other");
        translations.put("tech_env_testing", "Testing");
        translations.put("tech_env_frameworks", "Frameworks and Application Servers");
        translations.put("tech_env_front", "Front / Presentation");
        translations.put("tech_env_application_layer", "Application Layer");
        translations.put("tech_env_cloud_providers", "Cloud providers");
        translations.put("tech_env_video_streaming", "Libraries and protocols for video streaming");
        translations.put("tech_env_automation", "Automation");

        // Technical Environment Descriptions
        translations.put("tech_desc_proprietary_protocol", "Proprietary Protocol for contextual reasoning and orchestration");
        translations.put("tech_desc_supervision_figma", "Supervision of work in: Figma, Figma Flow");
        translations.put("tech_desc_proprietary_api_gateway", "proprietary API Gateway");
        translations.put("tech_desc_supervision_zeplin", "Supervision of work in: Zeplin, Sketch");
        translations.put("tech_desc_behavior_testing", "Behavior Testing:");
        translations.put("tech_desc_integration_testing", "Integration Testing:");
        translations.put("tech_desc_mock_testing", "Mock Testing:");
        translations.put("tech_desc_load_testing", "Load Testing:");
        translations.put("tech_desc_unit_test", "Unit Test:");
        translations.put("tech_desc_auto_static_analysis", "automatic static analysis on local IDE");
        translations.put("tech_desc_custom_dora_metrics", "custom DORA-like metrics tracking");
        translations.put("tech_desc_proprietary_auditing", "proprietary Auditing tool");
        translations.put("tech_desc_usage_proprietary_nlp", "Usage of proprietary NLP engine");
        translations.put("tech_desc_graph_libraries", "graph libraries");
        translations.put("tech_desc_drone_planners", "drone mission planners");
        translations.put("tech_desc_proprietary_tools_jolie", "proprietary tools for jolie-lang languages");
        translations.put("tech_desc_java_libs_legacy", "libraries to connect with legacy technologies like:");
        translations.put("tech_desc_proprietary_gis", "Proprietary GIS");
    }

    private void loadFrenchTranslations() {
        // Titles
        translations.put("main_title", "CHIEF TECHNOLOGY OFFICER - AI EXPERT");//
        translations.put("profile_title", "Profil");
        translations.put("competencies_title", "Compétences Clés");
        translations.put("experience_title", "Expériences Professionnelles");
        translations.put("education_title", "Éducation & Qualifications");
        translations.put("languages_title", "Langues");

        // Introduction
        translations.put("intro_1", "7 ans d'expérience en direction technique et en pilotage d'équipes R&D dans des PME.\n");
        if (TARGET_COMPANY_IS_A_STARTUP) {
            translations.put("intro_2", "4 startups créées from scratch en tant que CTO et associé minoritaire.\n");
        }
        translations.put("intro_3", "16 ans d'expérience dans la conception, le développement et le déploiement de systèmes distribués et de plateformes SaaS à haute disponibilité.\n");
//        translations.put("intro_3", "16 ans d'expérience dans la conception, le développement et le déploiement de systèmes distribués et de plateformes SaaS à haute disponibilité. " +
//                "**Expert en IA agentique, systèmes multi-agents et IA générative.**\n");
        translations.put("intro_4", "12 ans d’expérience internationale dans la réalisation de résultats à fort impact au sein d’équipes agiles et multiculturelles, réparties dans plusieurs pays.\n");
//        translations.put("intro_4", "12 ans de pratique au sein d’équipes agiles en tant que Senior Engineer et Scrum Master.\n");
//        translations.put("intro_5", "12 ans d’expérience internationale dans des environnements multiculturels exigeants à travers plusieurs pays.\n");
        String txtBase="10+ domaines d’application couverts tout au long de ma carrière";
        String textFinal=txtBase;
        if (TARGET_SECTOR_IS_EDUCATION_OR_EMPLOYABILITY_OR_SKILLS) {
        textFinal=txtBase+", dont 5 ans en EdTech, reconversion professionnelle et développement des compétences.";}
        else {
            textFinal=txtBase+".";
        }
        translations.put("intro_6",textFinal+"\n");

        translations.put("intro_7", "3 langues parlées avec une compétence professionnelle complète : français, anglais et italien.\n");
        translations.put("intro_8", "6 ans d’expérience comme consultant indépendant et Fractional CPTO.\n");
        translations.put("intro_9", "6 ans d’expérience en management, à la tête d’équipes jusqu’à 15 professionnels.\n");
        if (TARGET_COMPANY_IS_A_STARTUP) {
            translations.put("intro_10", "10 ans au sein des écosystèmes de startups, à travers des incubateurs et accélérateurs tels qu’Innovami, le Technoparc du CERN, Station F et la Pépinière 26..\n");
        }

        // Profile
        translations.put("profile_1", "Leader technologique orienté résultats, passionné par l'innovation, la cybersécurité et la transformation digitale des organisations.\n");
        translations.put("profile_2", "Curieux intellectuellement, apprenant rapide et leader d'équipe engagé, je combine une vision stratégique de la gouvernance IT (alignement métier / SI, gestion budgétaire, pilotage de la performance) avec une expertise opérationnelle en cloud, sécurité des systèmes d'information, architecture logicielle.\n");//et automatisation des processus
        translations.put("profile_3", "Habitué à conduire des projets complexes de modernisation du SI, d'intégration d'outils collaboratifs, et de migration vers des infrastructures scalables et résilientes.\n");
        translations.put("profile_4", "");

        // Competencies titles
        translations.put("comp_mgmt_title", "Management de projets IA et logiciels:\n");
        translations.put("comp_ai_title", "Technologies IA:\n");
        translations.put("comp_tech_title", "Autres Technologies:\n");
        translations.put("comp_arch_title", "Architecture logicielle:\n");
        translations.put("comp_cloud_ia_title", "Plateformes Cloud IA:\n");
        translations.put("comp_cloud_title", "Cloud:\n");
        translations.put("comp_front_title", "Front-end:\n");
        translations.put("comp_sec_title", "Sécurité et confidentialité:\n");
        translations.put("comp_dev_title", "Pratiques de développement:\n");

        // Competencies content
        translations.put("comp_mgmt", "Management R&D, Direction de projets complexes, Adoption de l'IA, Systèmes Agentiques, pipelines RAG personnalisés, assistants IA locaux, Systèmes Multi-Agents, Context Engineering.\n\n");
        translations.put("comp_ai", "Bibliothèques LLM (LangChain, LiteLLM, RouteLLM, Lark, FastAPI, mem0.ai), Ollama, vLLM, Docker-MCP, Model Context Protocol (MCP), protocole agent-to-agent (A2A), protocole AG-UI, Gradio, Streamlit, CoPilotKit, Bases de données vectorielles.\n\n");
        translations.put("comp_tech", "Python, Java, Spring Boot, JEE, Implémentation d'API, API Managers, Nginx, SQL, Bases de données relationnelles, NoSQL, MongoDB, Elasticsearch, Redis, Neo4J, RabbitMQ, Kafka, n8n.\n\n");
        translations.put("comp_arch", "Architecture microservices, Domain Driven Design, Architectures d'IA agentique, SOA (Architectures Orientées Services), Enterprise Integration Patterns, Frameworks MVC.\n\n");
        translations.put("comp_cloud_ia", "Google Vertex AI, RunPod, AnyScale, AWS Bedrock.\n\n");
        translations.put("comp_cloud", "Google Cloud Platform, Heroku, Netlify, Vercel, OVHCloud, Azure, AWS (S3, EC2), Scaleway, Docker, Docker Compose, Google Kubernetes Engine, Pipelines CI/CD, Outils d'observabilité (Google Cloud Monitoring etc).\n\n");
        translations.put("comp_front", "Angular, Next.js.\n\n");
        translations.put("comp_sec", "OAuth 2.0, OIDC, JWT, Fournisseurs d'identité, Mise en œuvre des recommandations OWASP et conformité RGPD, ISO/IEC-27001.\n\n");
        translations.put("comp_dev", "Test-Driven Development (TDD), Behavior-Driven Development (BDD), patterns de conception, d'architecture et agentiques.\n");

        // Education
        translations.put("education_1", "Ingénieur agréé (qualification professionnelle en Italie).");
        translations.put("education_2", "Master en ingénierie informatique - Université de Bologne, Italie.");
        translations.put("education_3", "Spécialisation : Systèmes et applications.");

        // Languages
        translations.put("lang_italian", "Italien : Langue maternelle   ");
        translations.put("lang_english", "Anglais : Compétence professionnelle   ");
        translations.put("Jan", "Janv.");
        translations.put("Feb", "Févr.");
        translations.put("Mar", "Mars");
        translations.put("Apr", "Avr.");
        translations.put("May", "Mai");
        translations.put("Jun", "Juin");
        translations.put("Jul", "Juil.");
        translations.put("Aug", "Août");
        translations.put("Sep", "Sept.");
        translations.put("Oct", "Oct.");
        translations.put("Nov", "Nov.");
        translations.put("Dec", "Déc.");
        translations.put("Pnt", "à ce jour");
        translations.put("contract_self_employed", "Indépendant");

        // PDF metadata
        translations.put("pdf_title", "Francesco Bullini - CV");
        translations.put("see_details", " (voir détails)");
        translations.put("go_to_original", "Retour au document principal");

        // Technical Environment
        translations.put("tech_env_title", "Environnement Technique");
        translations.put("tech_env_languages", "Langages");
        translations.put("tech_env_backend_framework", "Framework Backend");
        translations.put("tech_env_llm_libraries", "Bibliothèques LLM");
        translations.put("tech_env_llm_providers", "Fournisseurs LLM et API");
        translations.put("tech_env_protocols", "Protocoles");
        translations.put("tech_env_cloud", "Cloud");
        translations.put("tech_env_middleware_db", "Middleware et Bases de données");
        translations.put("tech_env_security", "Sécurité");
        translations.put("tech_env_databases", "Bases de données");
        translations.put("tech_env_semantic_web", "Web Sémantique/Ontologies");
        translations.put("tech_env_ux_ui", "UX/UI");
        translations.put("tech_env_authentication", "Authentification");
        translations.put("tech_env_ci_cd", "CI/CD");
        translations.put("tech_env_nlp_ml", "NLP / ML");
        translations.put("tech_env_no_code", "No-Code");
        translations.put("tech_env_other", "Autres");
        translations.put("tech_env_testing", "Tests");
        translations.put("tech_env_frameworks", "Frameworks et Serveurs d'Applications");
        translations.put("tech_env_front", "Front / Présentation");
        translations.put("tech_env_application_layer", "Couche Application");
        translations.put("tech_env_cloud_providers", "Fournisseurs Cloud");
        translations.put("tech_env_video_streaming", "Bibliothèques et protocoles pour streaming vidéo");
        translations.put("tech_env_automation", "Automatisation");

        // Technical Environment Descriptions
        translations.put("tech_desc_proprietary_protocol", "Protocole propriétaire pour le raisonnement contextuel et l'orchestration");
        translations.put("tech_desc_supervision_figma", "Supervision des travaux sur : Figma, Figma Flow");
        translations.put("tech_desc_proprietary_api_gateway", "API Gateway propriétaire");
        translations.put("tech_desc_supervision_zeplin", "Supervision des travaux sur : Zeplin, Sketch");
        translations.put("tech_desc_behavior_testing", "Tests comportementaux :");
        translations.put("tech_desc_integration_testing", "Tests d'intégration :");
        translations.put("tech_desc_mock_testing", "Tests de mock :");
        translations.put("tech_desc_load_testing", "Tests de charge :");
        translations.put("tech_desc_unit_test", "Tests unitaires :");
        translations.put("tech_desc_auto_static_analysis", "analyse statique automatique sur l'IDE local");
        translations.put("tech_desc_custom_dora_metrics", "suivi de métriques personnalisées de type DORA");
        translations.put("tech_desc_proprietary_auditing", "outil d'audit propriétaire");
        translations.put("tech_desc_usage_proprietary_nlp", "Utilisation d'un moteur NLP propriétaire");
        translations.put("tech_desc_graph_libraries", "bibliothèques de graphes");
        translations.put("tech_desc_drone_planners", "planificateurs de missions pour drones");
        translations.put("tech_desc_proprietary_tools_jolie", "outils propriétaires pour les langages jolie-lang");
        translations.put("tech_desc_java_libs_legacy", "bibliothèques pour se connecter avec des technologies comme :");
        translations.put("tech_desc_proprietary_gis", "SIG propriétaire");
    }

    public String get(String key) {
        return translations.getOrDefault(key, "");
    }

    public void put(String key, String value) {
        translations.put(key, value);
    }

    public String getLanguage() {
        return language;
    }
}
