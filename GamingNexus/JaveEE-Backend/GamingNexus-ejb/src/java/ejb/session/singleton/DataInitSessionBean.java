/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.CompanySessionBeanLocal;
import ejb.session.stateless.CustomerSessionBeanLocal;
import ejb.session.stateless.GameSessionBeanLocal;
import ejb.session.stateless.HardwareSessionBeanLocal;
import ejb.session.stateless.OtherSoftwareSessionBeanLocal;
import ejb.session.stateless.PromotionSessionBeanLocal;
import ejb.session.stateless.SaleTransactionSessionBeanLocal;
import ejb.session.stateless.SystemAdminSessionBeanLocal;
import ejb.session.stateless.TagSessionBeanLocal;
import entity.Category;
import entity.Company;
import entity.Customer;
import entity.Game;
import entity.Hardware;
import entity.OtherSoftware;
import entity.Product;
import entity.Promotion;
import entity.SaleTransaction;
import entity.SaleTransactionLineItem;
import entity.SystemAdmin;
import entity.Tag;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.exception.CategoryNotFoundException;
import util.exception.CompanyNotFoundException;
import util.exception.CompanyUsernameExistException;
import util.exception.CreateNewCategoryException;
import util.exception.CreateNewProductException;
import util.exception.CreateNewSaleTransactionException;
import util.exception.CreateNewTagException;
import util.exception.CustomerNotFoundException;
import util.exception.CustomerUsernameExistException;
import util.exception.InputDataValidationException;
import util.exception.ProductNotFoundException;
import util.exception.ProductSkuCodeExistException;
import util.exception.SystemAdminUsernameExistException;
import util.exception.TagNotFoundException;
import util.exception.UnknownPersistenceException;
import util.exception.UpdateProductException;

/**
 *
 * @author Jinyichen & Jingyuan
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB(name = "SaleTransactionSessionBeanLocal")
    private SaleTransactionSessionBeanLocal saleTransactionSessionBeanLocal;

    @EJB
    private PromotionSessionBeanLocal promotionSessionBean;

    @EJB(name = "OtherSoftwareSessionBeanLocal")
    private OtherSoftwareSessionBeanLocal otherSoftwareSessionBeanLocal;

    @EJB(name = "HardwareSessionBeanLocal")
    private HardwareSessionBeanLocal hardwareSessionBeanLocal;

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanlocal;

    @EJB
    private CompanySessionBeanLocal companySessionBeanLocal;

    @EJB(name = "GameSessionBeanLocal")
    private GameSessionBeanLocal gameSessionBeanLocal;

    @EJB
    private TagSessionBeanLocal tagSessionBeanLocal;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB(name = "SystemAdminSessionBeanLocal")
    private SystemAdminSessionBeanLocal systemAdminSessionBeanLocal;

    @PersistenceContext(unitName = "GamingNexus-ejbPU")
    private EntityManager em;

    public DataInitSessionBean() {
    }

    @PostConstruct
    public void postConstruct() {
        if (em.find(Game.class, 1l) == null) {
            initializeData();
            System.out.print("dataInit");
            System.out.println("**************Entered Post construct");
        }

    }

    private void initializeData() {

        try {
            
            SystemAdmin systemAdmin = new SystemAdmin("123456", "addr 1", "email@hotmail.com", "Singapore","admin1", "password");
            systemAdminSessionBeanLocal.createNewSystemAdmin(systemAdmin);
            
                        //Customer
            Customer customer1 = customerSessionBeanlocal.createCustomer(new Customer("7654321",
                    "Singapore", "customer1@gmail.com", "Singapore", "customer1", "password", "2003-09-20", "male"));
            Customer customer2 = customerSessionBeanlocal.createCustomer(new Customer("76543210",
                    "Singapore", "customer2@gmail.com", "Singapore", "customer2", "password", "1995-04-12", "male"));
            Customer customer3 = customerSessionBeanlocal.createCustomer(new Customer("765432101",
                    "Singapore", "customer3@gmail.com", "Singapore", "customer3", "password", "1997-11-11", "female"));
            Customer customer4 = customerSessionBeanlocal.createCustomer(new Customer("765432102",
                    "Singapore", "customer4@gmail.com", "Singapore", "customer4", "password", "2004-07-20", "female"));
            Customer customer5 = customerSessionBeanlocal.createCustomer(new Customer("765432103",
                    "Singapore", "customer5@gmail.com", "Singapore", "customer5", "password", "2007-06-20", "female"));

            Category categoryEntitySoftwareGame = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Game", "Game"), null);

            Category categoryEntitySoftwareTool = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Software", "Software"), null);
            Category categoryEntityAnimationModeling = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Animation & Modeling", "Animation & Modeling"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityAudio = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Audio", "Audio"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityDesign = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Design & Illustration", "Design & Illustration"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityEducation = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Education", "Education"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityGameDev = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Game Development", "Game Development"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityPhotoEditing = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Photo Editing", "Photo Editing"), categoryEntitySoftwareTool.getCategoryId());
            Category categoryEntityVideoProduction = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Video Production", "Video Production"), categoryEntitySoftwareTool.getCategoryId());

            Category categoryEntityHardware = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Hardware", "Hardware"), null);
            Category categoryEntityLaptop = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Laptop", "Laptop"), categoryEntityHardware.getCategoryId());
            Category categoryEntityMouse = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Mouse", "Mouse"), categoryEntityHardware.getCategoryId());
            Category categoryEntityKeyboard = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Keyboard", "Keyboard"), categoryEntityHardware.getCategoryId());
            Category categoryEntityMonitor = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Monitor", "Monitor"), categoryEntityHardware.getCategoryId());
            Category categoryEntityGraphicscard = categorySessionBeanLocal
                    .createNewCategoryEntity(new Category("Graphicscard", "Graphicscard"), categoryEntityHardware.getCategoryId());

            Tag tagEntityPopular = tagSessionBeanLocal.createNewTagEntity(new Tag("Popular", false));
            Tag tagEntityDiscount = tagSessionBeanLocal.createNewTagEntity(new Tag("Discount", false));
            Tag tagEntityNew = tagSessionBeanLocal.createNewTagEntity(new Tag("New", false));
            Tag tagEntityUpcoming = tagSessionBeanLocal.createNewTagEntity(new Tag("Upcoming", false));
            Tag tagEntitySlider = tagSessionBeanLocal.createNewTagEntity(new Tag("Slider", false));

            Tag tagEntityAction = tagSessionBeanLocal.createNewTagEntity(new Tag("Action", true));
            Tag tagEntityAdventure = tagSessionBeanLocal.createNewTagEntity(new Tag("Adventure", true));
            Tag tagEntityCasual = tagSessionBeanLocal.createNewTagEntity(new Tag("Casual", true));
            Tag tagEntitySimulation = tagSessionBeanLocal.createNewTagEntity(new Tag("Simulation", true));
            Tag tagEntityMultiplayer = tagSessionBeanLocal.createNewTagEntity(new Tag("Multiplayer", true));
            Tag tagEntitySingleplayer = tagSessionBeanLocal.createNewTagEntity(new Tag("Singleplayer", true));
            Tag tagEntitySports = tagSessionBeanLocal.createNewTagEntity(new Tag("Sports", true));
            Tag tagEntityRacing = tagSessionBeanLocal.createNewTagEntity(new Tag("Racing", true));
            Tag tagEntityStrategy = tagSessionBeanLocal.createNewTagEntity(new Tag("Strategy", true));
            Tag tagEntityRPG = tagSessionBeanLocal.createNewTagEntity(new Tag("RPG", true));
            Tag tagEntityFPS = tagSessionBeanLocal.createNewTagEntity(new Tag("FPS", true));
            Tag tagEntityMOBA = tagSessionBeanLocal.createNewTagEntity(new Tag("MOBA", true));
            Tag tagEntityPuzzle = tagSessionBeanLocal.createNewTagEntity(new Tag("Puzzle", true));
            Tag tagEntityOpenworld = tagSessionBeanLocal.createNewTagEntity(new Tag("Openworld", true));
            Tag tagEntityZombies = tagSessionBeanLocal.createNewTagEntity(new Tag("Zombies", true));
            Tag tagEntityHorror = tagSessionBeanLocal.createNewTagEntity(new Tag("Horror", true));
            Tag tagEntityFunny = tagSessionBeanLocal.createNewTagEntity(new Tag("Funny", true));

            Company company1 = companySessionBeanLocal.createNewCompany(new Company("123123", "Singapore",
                    "company1@gmail.com", "Singapore", "company1", "password"));
            List<Product> company1Products = new ArrayList<>();
            Company valve = companySessionBeanLocal.createNewCompany(new Company("425-889-9642", "PO BOX 1688 Bellevue, WA 98009",
                    "https://help.steampowered.com", "USA", "valve", "password"));
            List<Product> valveProducts = new ArrayList<>();
            Company activision = companySessionBeanLocal.createNewCompany(new Company("(310) 255-2000", "Santa Monica, California, United States",
                    "https://support.activision.com/contactus", "USA", "Activision", "password"));
            List<Product> activisionProducts = new ArrayList<>();
            Company microprose = companySessionBeanLocal.createNewCompany(new Company("none", "Maryland, United States",
                    "https://www.microprose.com/", "USA", "MicroProse", "password"));
            List<Product> microproseProducts = new ArrayList<>();
            Company rockstar = companySessionBeanLocal.createNewCompany(new Company("646-536-2842", "4 622, Broadway, New York, NY 10012, United States",
                    "https://www.rockstargames.com/", "USA", "Rockstar", "password"));
            List<Product> rockstarProducts = new ArrayList<>();
            Company ubisoft = companySessionBeanLocal.createNewCompany(new Company("6408 3000", "Montreuil, France",
                    "https://www.ubisoft.com/en-us/", "France", "Ubisoft", "password"));
            List<Product> ubisoftProducts = new ArrayList<>();

            List<Long> tags = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            String name = "Counter-Strike";
            LocalDate releaseDate = LocalDate.parse("2000-11-01", formatter);
            boolean parentAdvisory = false;
            double averageRating = 88;
            int sales = 13033334;
            double price = 10.00;
            String description = "Play the worlds number 1 online action game. Engage in an incredibly realistic brand of terrorist warfare in this wildly popular team-based game. Ally with teammates to complete strategic missions. Take out enemy sites. Rescue hostages. Your role affects your teams success. Your teams success affects your role.";
            String headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/10/header.jpg?t=1447887426";
            String computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            String videoLink = "";
            Long categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game cs = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(cs);
            
            
            //Slider list
            name = "Sid Meier's Civilization VI";
            releaseDate = LocalDate.parse("2016-10-21", formatter);
            parentAdvisory = false;
            averageRating = 88;
            sales = 1363057;
            price = 74.90;
            description = "Civilization VI offers new ways to engage with your world: cities now physically expand across the map, active research in technology and culture unlocks new potential, and competing leaders will pursue their own agendas based on their historical traits as you race for one of five ways to achieve victory in the game.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/289070/header.jpg?t=1477354938";
            computerRequirements = "Minimum:OS: Windows 7x64 / Windows 8.1x64 / Windows 10x64Processor: Intel Core i3 2.5 Ghz or AMD Phenom II 2.6 Ghz or greaterMemory: 4 GB RAMGraphics: 1 GB & AMD 5570 or nVidia 450DirectX: Version 11Storage: 12 GB available spaceSound Card: DirectX Compatible Sound Device";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256672695/movie480.webm?t=1476737061";
            tags = new ArrayList<>();
            tags.add(tagEntitySlider.getTagId());
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civVI = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, microprose.getUserId());
            microproseProducts.add(civVI);
            
            
            name = "Grand Theft Auto V";
            releaseDate = LocalDate.parse("2015-04-13", formatter);
            parentAdvisory = true;
            averageRating = 96;
            sales = 5886074;
            price = 40.00;
            description = "When a young street hustler a retired bank robber and a terrifying psychopath find themselves entangled with some of the most frightening and deranged elements of the criminal underworld the U.S. government and the entertainment industry they must pull off a series of dangerous heists to survive in a ruthless city in which they can trust nobody least of all each other. Grand Theft Auto V for PC offers players the option to explore the award-winning world of Los Santos and Blaine County in resolutions of up to 4k and beyond as well as the chance to experience the game running at 60 frames per second.  The game offers players a huge range of PC-specific customization options including over 25 separate configurable settings for texture quality shaders tessellation anti-aliasing and more as well as support and extensive customization for mouse and keyboard controls. Additional options include a population density slider to control car and pedestrian traffic as well as dual and triple monitor support 3D compatibility and plug-and-play controller support.   Grand Theft Auto V for PC also includes Grand Theft Auto Online with support for 30 players and two spectators. Grand Theft Auto Online for PC will include all existing gameplay upgrades and Rockstar-created content released since the launch of Grand Theft Auto Online including Heists and Adversary modes. The PC version of Grand Theft Auto V and Grand Theft Auto Online features First Person Mode giving players the chance to explore the incredibly detailed world of Los Santos and Blaine County in an entirely new way. Grand Theft Auto V for PC also brings the debut of the Rockstar Editor a powerful suite of creative tools to quickly and easily capture edit and share game footage from within Grand Theft Auto V and Grand Theft Auto Online. The Rockstar Editors Director Mode allows players the ability to stage their own scenes using prominent story characters pedestrians and even animals to bring their vision to life. Along with advanced camera manipulation and editing effects including fast and slow motion and an array of camera filters players can add their own music using songs from GTAV radio stations or dynamically control the intensity of the games score. Completed videos can be uploaded directly from the Rockstar Editor to YouTube and the Rockstar Games Social Club for easy sharing.   Soundtrack artists The Alchemist and Oh No return as hosts of the new radio station The Lab FM. The station features new and exclusive music from the production duo based on and inspired by the games original soundtrack. Collaborating guest artists include Earl Sweatshirt Freddie Gibbs Little Dragon Killer Mike Sam Herring from Future Islands and more. Players can also discover Los Santos and Blaine County while enjoying their own music through Self Radio a new radio station that will host player-created custom soundtracks. Existing players on PlayStation(r)3 PlayStation(r)4 Xbox 360 and Xbox One are able to transfer their Grand Theft Auto Online characters and progression to PC.  For more information please visit rockstargames.com/gtaonline/charactertransfer.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/271590/header.jpg?t=1470324156";
            computerRequirements = "Minimum:OS: Windows 8.1 64 Bit Windows 8 64 Bit Windows 7 64 Bit Service Pack 1 Windows Vista 64 Bit Service Pack 2* (*NVIDIA video card recommended if running Vista OS)Processor: Intel Core 2 Quad CPU Q6600 @ 2.40GHz (4 CPUs) / AMD Phenom 9850 Quad-Core Processor (4 CPUs) @ 2.5GHzMemory: 4 GB RAMGraphics: NVIDIA 9800 GT 1GB / AMD HD 4870 1GB (DX 10 10.1 11)Storage: 65 GB available spaceSound Card: 100% DirectX 10 compatibleAdditional Notes: Over time downloadable content and programming changes will change the system requirements for this game.  Please refer to your hardware manufacturer and www.rockstargames.com/support for current compatibility information. Some system components such as mobile chipsets integrated and AGP graphics cards may be incompatible. Unlisted specifications may not be supported by publisher.     Other requirements:  Installation and online play requires log-in to Rockstar Games Social Club (13+) network; internet connection required for activation online play and periodic entitlement verification; software installations required including Rockstar Games Social Club platform DirectX  Chromium and Microsoft Visual C++ 2008 sp1 Redistributable Package and authentication software that recognizes certain hardware attributes for entitlement digital rights management system and other support purposes.     SINGLE USE SERIAL CODE REGISTRATION VIA INTERNET REQUIRED; REGISTRATION IS LIMITED TO ONE ROCKSTAR GAMES SOCIAL CLUB ACCOUNT (13+) PER SERIAL CODE; ONLY ONE PC LOG-IN ALLOWED PER SOCIAL CLUB ACCOUNT AT ANY TIME; SERIAL CODE(S) ARE NON-TRANSFERABLE ONCE USED; SOCIAL CLUB ACCOUNTS ARE NON-TRANSFERABLE.  Partner Requirements:  Please check the terms of service of this site before purchasing this software.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256703142/movie_max.mp4?t=1513275242";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySlider.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game gtaV = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, rockstar.getUserId());
            rockstarProducts.add(gtaV);
            
            name = "Watch Dogs 2";
            releaseDate = LocalDate.parse("2016-11-29", formatter);
            parentAdvisory = true;
            averageRating = 86;
            sales = 147605;
            price = 70.00;
            description = "Play as Marcus Holloway, a brilliant young hacker living in the birthplace of the tech revolution, the San Francisco Bay Area. Team up with Dedsec, a notorious group of hackers, to execute the biggest hack in history; take down ctOS 2.0, an invasive operating system being used by criminal masterminds to monitor and manipulate citizens on a massive scale.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/447040/header.jpg?t=1481044335";
            computerRequirements = "Minimum:OS: Windows 7 SP1 Windows 8.1 Windows 10 (64bit versions only)Processor: Intel Core i5 2400s @ 2.5 GHz AMD FX 6120 @ 3.5 GHz or betterMemory: 6 GB RAMGraphics: NVIDIA GeForce GTX 660 with 2 GB VRAM or AMD Radeon HD 7870 with 2 GB VRAM or better - See supported List*Network: Broadband Internet connectionStorage: 50 GB available spaceSound Card: DirectX compatible using the latest driversAdditional Notes: Periphericals: Microsoft Xbox One Controller DUALSHOCK(r) 4 Controller Windows-compatible keyboard mouse optional controller / Multiplayer: 256 kbps or faster broadband connection / Note: This product supports 64-bit operating systems only. Laptop versions of these cards may work but are not officially supported. For the most up-to-date requirement listings please visit the FAQ on our support website at support.ubi.com.  High speed internet access and a valid Ubisoft account are required to activate the game after installation to authenticate your system and continue gameplay after any re-activation access online features play online or unlock exclusive content.";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySlider.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            Game watchdogs2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, ubisoft.getUserId());
            ubisoftProducts.add(watchdogs2);
            
            name = "NBA 2K20";
            releaseDate = LocalDate.parse("2019-09-05", formatter);
            parentAdvisory = false;
            averageRating = 74;
            sales = 492271;
            price = 81.90;
            description = "NBA 2K has evolved into much more than a basketball simulation. 2K continues to redefine what’s possible in sports gaming with NBA 2K20, featuring best in class graphics & gameplay, ground breaking game modes, and unparalleled player control and customization. Plus, with its immersive open-world Neighborhood, NBA 2K20 is a platform for gamers and ballers to come together and create what’s next in basketball culture.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/1089350/header.jpg?t=1573674198";
            computerRequirements = "OS: Windows 7 64-bit, Windows 8.1 64-bit or Windows 10 64-bit";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySlider.getTagId());
            tags.add(tagEntitySports.getTagId());
            tags.add(tagEntitySimulation.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game nba2k20 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, activision.getUserId());
            activisionProducts.add(nba2k20);
                    
            name = "Prototype";
            releaseDate = LocalDate.parse("2009-06-10", formatter);
            parentAdvisory = true;
            averageRating = 79;
            sales = 482271;
            price = 20.00;
            description = "You are the Prototype Alex Mercer a man without memory armed with amazing shape-shifting abilities hunting your way to the heart of the conspiracy which created you; making those responsible pay.                    Fast & Deadly Shape-Shifting Combat: Reconfigure your body to the situation at hand. From Claws to Blades to Hammers to Whips choose the right weapon for the situation. Change to a shield or armor for defense or use advanced sensory powers (thermal vision infected vision) to track your enemies                    Over-the-Top Locomotion & Agility: Seamlessly and fluidly bound from building to building run up walls bounce off cars and everything in your path. Adaptive parkour lets you move freely through the open-world environments of New York City.                    Unique Disguising Abilities: Consume anyone at anytime take on their appearances and assume their memories and special abilities.                    Deep Conspiracy-Driven Storyline: Wake up with no memory of the past...just mysterious powers and a link to a town in Idaho. Delve into the mysteries of your origin the true nature of your power and your part in a conspiracy 40 years in the making.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/10150/header.jpg?t=1464729751";
            computerRequirements = "OS: Windows(r) XP (with Service Pack 3) or Windows Vista(r)                    Processor: Intel(r) Core(tm)2 Duo 2.6 GHz or AMD Athlon(tm) 64 X2 4000+ or better                    Memory: Vista 2 GB RAM / XP 1 GB RAM                    Graphics: All NVIDIA(r) GeForce(r) 7800 GT 256 MB and better chipsets. All ATI Radeon(tm) X1800 256 MB and better chipsets                    DirectX(r): Microsoft DirectX 9.0c                    Hard Drive: 8GB of free hard drive space                    Sound: DirectX(r) 9.0c compliant sound card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySlider.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game prototype = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, activision.getUserId());
            activisionProducts.add(prototype);
            
            
            //Popular list
            name = "PLAYERUNKNOWN'S BATTLEGROUNDS";
            releaseDate = LocalDate.parse("2017-12-21", formatter);
            parentAdvisory = false;
            averageRating = 86;
            sales = 23033334;
            price = 35.00;
            description = "PLAYERUNKNOWN'S BATTLEGROUNDS is a battle royale shooter that pits 100 players against each other in a struggle for survival. Gather supplies and outwit your opponents to become the last person standing.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/578080/header.jpg?t=1587582005";
            computerRequirements = "Minimum:OS: Windows 7 64bit Processor: Intel Core i3 or similar";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game pubg = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(pubg);
            
            name = "Assassin's Creed Unity";
            releaseDate = LocalDate.parse("2014-11-13", formatter);
            parentAdvisory = false;
            averageRating = 86;
            sales = 3033334;
            price = 35.00;
            description = "Assassin’s Creed Unity is an action/adventure game set in the city of Paris during one of its darkest hours, the French Revolution. Take ownership of the story by customising Arno's equipement to make the experience unique to you, both visually and mechanically. In addition to an epic single-player experience, Assassin’s Creed Unity delivers the excitement of playing with up to three friends through online cooperative gameplay in specific missions. Throughout the game, take part in one of the most pivotal moments of French history in a compelling storyline and a breath-taking playground that brought you the city of lights of today.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/289650/header.jpg?t=1575045099";
            computerRequirements = "Minimum:OS: Windows 7 64bit Processor: Intel Core i3 or similar";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game unity = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, ubisoft.getUserId());
            ubisoftProducts.add(unity);
            
            name = "Borderlands 3";
            releaseDate = LocalDate.parse("2020-03-13", formatter);
            parentAdvisory = false;
            averageRating = 81;
            sales = 645334;
            price = 81.00;
            description = "The original shooter-looter returns, packing bazillions of guns and a mayhem-fueled adventure! Blast through new worlds & enemies and save your home from the most ruthless cult leaders in the galaxy.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/397540/header.jpg?t=1587675179";
            computerRequirements = "Minimum:OS: Windows 7 64bit Processor: Intel Core i3 or similar";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityNew.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityRPG.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game borderland = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(borderland);
            
            name = "Ori and the Will of the Wisps";
            releaseDate = LocalDate.parse("2020-03-13", formatter);
            parentAdvisory = false;
            averageRating = 81;
            sales = 645334;
            price = 40.00;
            description = "The little spirit Ori is no stranger to peril, but when a fateful flight puts the owlet Ku in harm’s way, it will take more than bravery to bring a family back together, heal a broken land, and discover Ori’s true destiny. From the creators of the acclaimed action-platformer Ori and the Blind Forest comes the highly anticipated sequel.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/1057090/header.jpg?t=1585866525";
            computerRequirements = "Minimum:OS: Windows 7 64bit Processor: Intel Core i3 or similar";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityNew.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game ori2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(ori2);
            
            name = "The Elder Scrolls V: Skyrim Special Edition";
            releaseDate = LocalDate.parse("2016-10-28", formatter);
            parentAdvisory = false;
            averageRating = 74;
            sales = 5791198;
            price = 53.90;
            description = "Winner of more than 200 Game of the Year Awards, Skyrim Special Edition brings the epic fantasy to life in stunning detail. The Special Edition includes the critically acclaimed game and add-ons with all-new features like remastered art and effects, volumetric god rays, dynamic depth of field, screen-space reflections, and more. Skyrim Special Edition also brings the full power of mods to the PC and consoles. New quests, environments, characters, dialogue, armor, weapons and more – with Mods, there are no limits to what you can experience.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/489830/header.jpg?t=1473436209";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel i5-750/AMD Phenom II X4-945 Memory: 8 GB RAM Graphics: NVIDIA GTX 470 1GB /AMD HD 7870 2GB or better Storage: 12 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityRPG.getTagId());
            Game elderscrollsV = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(elderscrollsV);
            
            name = "Need For Speed: Hot Pursuit";
            releaseDate = LocalDate.parse("2010-11-28", formatter);
            parentAdvisory = false;
            averageRating = 86;
            sales = 2791199;
            price = 26.90;
            description = "Need for Speed Hot Pursuit launches you into a new open-world landscape behind the wheel of the world's fastest and most beautiful cars. From Criterion, the award-winning studio behind the Burnout series, Hot Pursuit will redefine racing games for a whole new generation.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/47870/header.jpg?t=1588270654";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel i5-750/AMD Phenom II X4-945 Memory: 8 GB RAM Graphics: NVIDIA GTX 470 1GB /AMD HD 7870 2GB or better Storage: 12 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityRacing.getTagId());
            Game needforspeed = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(needforspeed);
            
            name = "Overcooked! 2";
            releaseDate = LocalDate.parse("2018-08-08", formatter);
            parentAdvisory = false;
            averageRating = 80;
            sales = 5791199;
            price = 22.00;
            description = "Overcooked returns with a brand-new helping of chaotic cooking action! Journey back to the Onion Kingdom and assemble your team of chefs in classic couch co-op or online play for up to four players. Hold onto your aprons … it’s time to save the world (again!)";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/728880/header.jpg?t=1587489613";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel i5-750/AMD Phenom II X4-945 Memory: 8 GB RAM Graphics: NVIDIA GTX 470 1GB /AMD HD 7870 2GB or better Storage: 12 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityCasual.getTagId());
            Game overcook = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(overcook);
            
            name = "The Crew 2";
            releaseDate = LocalDate.parse("2018-06-29", formatter);
            parentAdvisory = false;
            averageRating = 80;
            sales = 3491199;
            price = 58.00;
            description = "The newest iteration in the revolutionary franchise, The Crew® 2 captures the thrill of the American motorsports spirit in one of the most exhilarating open worlds ever created. Welcome to Motornation, a huge, varied, action-packed, and beautiful playground built for motorsports throughout the entire US of A. Enjoy unrestrained exploration on ground, sea, and sky. From coast to coast, street and pro racers, off-road explorers, and freestylers gather and compete in all kinds of disciplines. Join them in high-octane contests and share every glorious moment with the world.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/646910/header.jpg?t=1586868345";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel i5-750/AMD Phenom II X4-945 Memory: 8 GB RAM Graphics: NVIDIA GTX 470 1GB /AMD HD 7870 2GB or better Storage: 12 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityRacing.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            Game crew2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, ubisoft.getUserId());
            ubisoftProducts.add(crew2);
            
            name = "Kingdom Rush Frontiers";
            releaseDate = LocalDate.parse("2016-07-25", formatter);
            parentAdvisory = false;
            averageRating = 82;
            sales = 491199;
            price = 10.00;
            description = "Bigger and badder than ever before, Kingdom Rush: Frontiers is a whole new level of the furiously fast, enchantingly charming gameplay that made the original title an award-winning hit. Command your troops through an epic (mis)adventure as you defend exotic lands from dragons, man-eating plants, and ghastly denizens of the underworld -all with flashy new towers, levels, heroes, and more goodies to help you crush your foes to a pulp.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/458710/header.jpg?t=1560522460";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel i5-750/AMD Phenom II X4-945 Memory: 8 GB RAM Graphics: NVIDIA GTX 470 1GB /AMD HD 7870 2GB or better Storage: 12 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityStrategy.getTagId());
            Game kingdomrush = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(kingdomrush);

            name = "Portal";
            releaseDate = LocalDate.parse("2007-10-10", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 9795716;
            price = 10.00;
            description = "Portal is a new single player game from Valve. Set in the mysterious Aperture Science Laboratories Portal has been called one of the most innovative new games on the horizon and will offer gamers hours of unique gameplay.The game is designed to change the way players approach manipulate and surmise the possibilities in a given environment; similar to how Half-Life?? 2s Gravity Gun innovated new ways to leverage an object in any given situation.Players must solve physical puzzles and challenges by opening portals to maneuvering objects and themselves through space.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/400/header.jpg?t=1447890222";
            computerRequirements = "Minimum: 1.7 GHz Processor 512MB RAM DirectX 8.1 level Graphics Card (Requires support for SSE) Windows 7 (32/64-bit)/Vista/XP Mouse Keyboard Internet ConnectionRecommended: Pentium 4 processor (3.0GHz or better) 1GB RAM DirectX 9 level Graphics Card Windows 7 (32/64-bit)/Vista/XP Mouse Keyboard Internet Connection";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPuzzle.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game portal = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(portal);

            name = "Dota 2";
            releaseDate = LocalDate.parse("2013-07-09", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 90687580;
            price = 0.00;
            description = "Dota is a competitive game of action and strategy played both professionally and casually by millions of passionate fans worldwide. Players pick from a pool of over a hundred heroes forming two teams of five players. Radiant heroes then battle their Dire counterparts to control a gorgeous fantasy landscape waging campaigns of cunning stealth and outright warfare.Irresistibly colorful on the surface Dota is a game of infinite depth and complexity. Every hero has an array of skills and abilities that combine with the skills of their allies in unexpected ways to ensure that no game is ever remotely alike. This is one of the reasons that the Dota phenomenon has continued to grow. Originating as a fan-made Warcraft 3 modification Dota was an instant underground hit. After coming to Valve the original community developers have bridged the gap to a more inclusive audience so that the rest of the world can experience the same core gameplay but with the level of polish that only Valve can provide.Get a taste of the game that has enthralled millions.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/570/header.jpg?t=1471028674";
            computerRequirements = "Minimum:OS: Windows 7 or newerProcessor: Dual core from Intel or AMD at 2.8 GHzMemory: 4 GB RAMGraphics: nVidia GeForce 8600/9600GT ATI/AMD Radeon HD2600/3600DirectX: Version 9.0cNetwork: Broadband Internet connectionStorage: 15 GB available spaceSound Card: DirectX Compatible";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256692017/movie480_vp9.webm?t=1586312838";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityMOBA.getTagId());
            Game dota2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(dota2);

            name = "Team Fortress 2";
            releaseDate = LocalDate.parse("2007-10-10", formatter);
            parentAdvisory = false;
            averageRating = 92;
            sales = 37878812;
            price = 0.00;
            description = "The most fun you can have online - PC Gamer Is now FREE! Theres no catch! Play as much as you want as long as you like! The most highly-rated free game of all time! One of the most popular online action games of all time Team Fortress 2 delivers constant free updates--new game modes maps equipment and most importantly hats. Nine distinct classes provide a broad range of tactical abilities and personalities and lend themselves to a variety of player skills. New to TF? Dont sweat it! No matter what your style and experience weve got a character for you. Detailed training and offline practice modes will help you hone your skills before jumping into one of TF2s many game modes including Capture the Flag Control Point Payload Arena King of the Hill and more. Make a character your own! There are hundreds of weapons hats and more to collect craft buy and trade. Tweak your favorite class to suit your gameplay style and personal taste. You dont need to pay to win--virtually all of the items in the Mann Co. Store can also be found in-game.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/440/header.jpg?t=1473198172";
            computerRequirements = "Minimum:OS: Windows(r) 7 (32/64-bit)/Vista/XPProcessor: 1.7 GHz Processor or betterMemory: 512 MB RAMDirectX: Version 8.1Network: Broadband Internet connectionStorage: 15 GB available spaceAdditional Notes: Mouse Keyboard";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256698790/movie480.webm?t=1519429751";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game teamFortress2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(teamFortress2);

            name = "Counter-Strike: Global Offensive";
            releaseDate = LocalDate.parse("2012-08-21", formatter);
            parentAdvisory = false;
            averageRating = 83;
            sales = 45833156;
            price = 15.00;
            description = "Counter-Strike: Global Offensive (CS: GO) will expand upon the team-based action gameplay that it pioneered when it was launched 14 years ago.CS: GO features new maps characters and weapons and delivers updated versions of the classic CS content (de_dust etc.). In addition CS: GO will introduce new gameplay modes matchmaking leader boards and more.Counter-Strike took the gaming industry by surprise when the unlikely MOD became the most played online PC action game in the world almost immediately after its release in August 1999 said Doug Lombardi at Valve. For the past 12 years it has continued to be one of the most-played games in the world headline competitive gaming tournaments and selling over 25 million units worldwide across the franchise. CS: GO promises to expand on CS award-winning gameplay and deliver it to gamers on the PC as well as the next gen consoles and the Mac.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/730/header.jpg?t=1467065027";
            computerRequirements = "Minimum:OS: Windows(r) 7/Vista/XPProcessor: Intel(r) Core(tm) 2 Duo E6600 or AMD Phenom(tm) X3 8750 processor or betterMemory: 2 GB RAMGraphics: Video card must be 256 MB or more and should be a DirectX 9-compatible with support for Pixel Shader 3.0DirectX: Version 9.0cStorage: 8 GB available space";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/81958/movie_max.webm?t=1554409259";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game csgo = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(csgo);

            name = "Portal 2";
            releaseDate = LocalDate.parse("2011-04-18", formatter);
            parentAdvisory = false;
            averageRating = 95;
            sales = 8741499;
            price = 20.00;
            description = "Portal 2 draws from the award-winning formula of innovative gameplay story and music that earned the original Portal over 70 industry accolades and created a cult following.The single-player portion of Portal 2 introduces a cast of dynamic new characters a host of fresh puzzle elements and a much larger set of devious test chambers. Players will explore never-before-seen areas of the Aperture Science Labs and be reunited with GLaDOS the occasionally murderous computer companion who guided them through the original game.The games two-player cooperative mode features its own entirely separate campaign with a unique story test chambers and two new player characters. This new mode forces players to reconsider everything they thought they knew about portals. Success will require them to not just act cooperatively but to think cooperatively.Product FeaturesExtensive single player: Featuring next generation gameplay and a wildly-engrossing story.Complete two-person co-op: Multiplayer game featuring its own dedicated story characters and gameplay.Advanced physics: Allows for the creation of a whole new range of interesting challenges producing a much larger but not harder game.Original music.Massive sequel: The original Portal was named 2007s Game of the Year by over 30 publications worldwide. Editing Tools: Portal 2 editing tools will be included.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/620/header.jpg?t=1452903431";
            computerRequirements = "Minimum:OS: Windows 7 / Vista / XPProcessor: 3.0 GHz P4 Dual Core 2.0 (or higher) or AMD64X2 (or higher)Memory: 2 GB RAMGraphics: Video card must be 128 MB or more and with support for Pixel Shader 2.0b (ATI Radeon X800 or higher / NVIDIA GeForce 7600 or higher / Intel HD Graphics 2000 or higher).DirectX: Version 9.0cStorage: 8 GB available spaceSound Card: DirectX 9.0c compatible";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/5790/movie480.webm?t=1452903127";
            tags = new ArrayList<>();
            tags.add(tagEntityPuzzle.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game portal2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(portal2);

            name = "Day of Defeat";
            releaseDate = LocalDate.parse("2003-05-01", formatter);
            parentAdvisory = false;
            averageRating = 79;
            sales = 7621102;
            price = 5.00;
            description = "Enlist in an intense brand of Axis vs. Allied teamplay set in the WWII European Theatre of Operations. Players assume the role of light/assault/heavy infantry sniper or machine-gunner class each with a unique arsenal of historical weaponry at their disposal. Missions are based on key historical operations. And as war rages players must work together with their squad to accomplish a variety of mission-specific objectives.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/30/header.jpg?t=1447350812";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            videoLink = "";
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game dayofdefeat = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(dayofdefeat);

            name = "Left 4 Dead";
            releaseDate = LocalDate.parse("2008-11-17", formatter);
            parentAdvisory = false;
            averageRating = 89;
            sales = 4473517;
            price = 10.00;
            description = "From Valve (the creators of Counter-Strike Half-Life and more) comes Left 4 Dead a co-op action horror game for the PC and Xbox 360 that casts up to four players in an epic struggle for survival against swarming zombie hordes and terrifying mutant monsters. Set in the immediate aftermath of the zombie apocalypse L4Ds survival co-op mode lets you blast a path through the infected in four unique movies guiding your survivors across the rooftops of an abandoned metropolis through rural ghost towns and pitch-black forests in your quest to escape a devastated Ground Zero crawling with infected enemies. Each movie is comprised of five large maps and can be played by one to four human players with an emphasis on team-based strategy and objectives.                     New technology dubbed the AI Director is used to generate a unique gameplay experience every time you play. The Director tailors the frequency and ferocity of the zombie attacks to your performance putting you in the middle of a fast-paced but not overwhelming Hollywood horror movie.                    Addictive single player co-op and multiplayer action gameplay from the makers of Counter-Strike and Half-Life                    Versus Mode lets you compete four-on-four with friends playing as a human trying to get rescued or as a zombie boss monster that will stop at nothing to destroy them.See how long you and your friends can hold out against the infected horde in the new Survival ModeAn advanced AI director dynamically creates intense and unique experiences every time the game is played                     20 maps 10 weapons and unlimited possibilities in four sprawling movies                     Matchmaking stats rankings and awards system drive collaborative play                     Designers Commentary allows gamers to go behind the scenes of the game                     Powered by Source and Steam                     THE SACRIFICEThe Sacrifice is  the new add-on for Left 4 Dead.The Sacrifice is the prequel to The Passing and takes place from the L4D Survivors perspective as they make their way South. In addition to advancing the story The Sacrifice introduces a new style finale featuring Sacrificial Gameplay where players get to decide who will give their life so the others may live.In The Sacrifice for Left 4 Dead owners receive The Sacrifice campaign playable in Campaign Versus  and Survival modes.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/500/header.jpg?t=1447903113";
            computerRequirements = "Supported OS: Windows(r) 7 32/64-bit / Vista 32/64 / XP  Processor: Pentium 4 3.0GHz  Memory: 1 GB  Graphics: 128 MB Shader model 2.0 ATI 9600 NVidia 6600 or better                     Hard Drive: At least 7.5 GB of free space  Sound Card: DirectX 9.0c compatible sound card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityZombies.getTagId());
            Game left4dead = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(left4dead);

            name = "Left 4 Dead 2";
            releaseDate = LocalDate.parse("2009-11-16", formatter);
            parentAdvisory = false;
            averageRating = 89;
            sales = 15574539;
            price = 10.00;
            description = "Set in the zombie apocalypse, Left 4 Dead 2 (L4D2) is the highly anticipated sequel to the award-winning Left 4 Dead, the #1 co-op game of 2008. This co-operative action horror FPS takes you and your friends through the cities, swamps and cemeteries of the Deep South, from Savannah to New Orleans across five expansive campaigns.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/550/header.jpg?t=1467131981";
            computerRequirements = "Minimum:OS: Windows(r) 7 32/64-bit / Vista 32/64 / XPProcessor: Pentium 4 3.0GHzMemory: 2 GB RAMGraphics: Video card with 128 MB Shader model 2.0. ATI X800 NVidia 6600 or betterDirectX: Version 9.0cStorage: 13 GB available spaceSound Card: DirectX 9.0c compatible sound card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityZombies.getTagId());
            Game left4dead2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(left4dead2);

            name = "DOOM II: Hell on Earth";
            releaseDate = LocalDate.parse("2007-08-03", formatter);
            parentAdvisory = false;
            averageRating = 83;
            sales = 443890;
            price = 5.00;
            description = "Let the Obsession begin. Again.This time the entire forces of the netherworld have overrun Earth. To save her you must descend into the stygian depths of Hell itself!Battle mightier nastier deadlier demons and monsters. Use more powerful weapons. Survive more mind-blowing explosions and more of the bloodiest fiercest most awesome blastfest ever!Play DOOM II solo with two people over a modem or with up to four players over a LAN (supporting IPX protocol). No matter which way you choose get ready for adrenaline-pumping action-packed excitement thats sure to give your heart a real workout.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/2300/header.jpg?t=1449848674";
            computerRequirements = "Minimum: A 100% Windows XP/Vista-compatible computer system";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game doom2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(doom2);


            

            name = "Sid Meier's Civilization V";
            releaseDate = LocalDate.parse("2010-09-21", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 9711421;
            price = 29.00;
            description = "The Flagship Turn-Based Strategy Game ReturnsBecome Ruler of the World by establishing and leading a civilization from the dawn of man into the space age: Wage war conduct diplomacy discover new technologies go head-to-head with some of historys greatest leaders and build the most powerful empire the world has ever known.INVITING PRESENTATION: Jump right in and play at your own pace with an intuitive interface that eases new players into the game. Civ veterans will appreciate the depth detail and control that are highlights of the series.BELIEVABLE WORLD: Ultra realistic graphics showcase lush landscapes for you to explore battle over and claim as your own. Art deco influences abound in the menus and icons in the most well-designed Civ ever developed.COMMUNITY & MULTIPLAYER: Compete with Civ players all over the world or locally in LAN matches mod* the game in unprecedented ways and install mods directly from an in-game community hub without ever leaving the game. Civilization V brings community to the forefront.WIDE SYSTEM COMPATIBILITY: Civilization V operates on many different systems from high end DX11 desktops to many laptops. Enjoy unlimited installations on multiple PCs with your Steam account and take your Civ V experience with you everywhere you go.ALL NEW FEATURES: A new hex-based gameplay grid opens up exciting new combat and build strategies. City States become a new resource in your diplomatic battleground. An improved diplomacy system allows you to negotiate with fully interactive leaders. Custom music scores and orchestral recordings give Civ V the level of polish and quality you expect from the series.SOCIALLY RESPONSIBLE: 2K Games is donating a total of $250000 to four education based charities and users choices will determine how the money is dispersed: simply select your choice from the pre-selected charities during the install process.+*Modding SDK available post launch as a free download.+ Charity selection available until Dec. 31 2010. Not valid in all territories.    Note: The Mac and Linux + SteamOS versions of Sid Meiers Civilization V are available in English French Italian German and Spanish only.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/8930/header.jpg?t=1473370865";
            computerRequirements = "Minimum:OS: Windows(r) Vista SP2/ Windows(r) 7 Processor: Intel Core 2 Duo 1.8 GHz or AMD Athlon X2 64 2.0 GHzMemory: 2GB RAM Graphics:256 MB ATI HD2600 XT or better 256 MB nVidia 7900 GS or better or Core i3 or better integrated graphics DirectX(r): DirectX(r) version 9.0c Hard Drive: 8 GB Free Sound: DirectX 9.0c-compatible sound card Note: Optimized for the touch-screen Ultrabook(tm) device";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civV = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, microprose.getUserId());
            microproseProducts.add(civV);

            name = "Sid Meier's Civilization IV";
            releaseDate = LocalDate.parse("2005-10-25", formatter);
            parentAdvisory = false;
            averageRating = 94;
            sales = 1422601;
            price = 20.00;
            description = "With over 6 million units sold and unprecedented critical acclaim from fans and press around the world, Sid Meier's Civilization is recognized as one of the greatest PC game franchises of all-time. Now, Sid Meier and Firaxis Games will take this incredibly fun and addictive game to new heights by adding new ways to play and win, new tools to manage and expand your civilization, all-new easy to use mod capabilities and intense multiplayer modes and options*. Civilization IV will come to life like never before in a beautifully detailed, living 3D world that will elevate the gameplay experience to a whole new level. Civilization IV has already been heralded as one of the top ten games of 2005, and a must-have for gamers around the globe!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/3900/header.jpg?t=1447366360";
            computerRequirements = "Minimum: Windows 2000/XP 1.2GHz Intel Pentium 4 or AMD Athlon processor or equivalent 256MB RAM 64 MB Video Card w/ Hardware T&L (GeForce 2/Radeon 7500 or better) DirectX7 compatible sound card 1.7GB of free hard drive space DirectX9.0c (included)Recommended: Windows 2000/XP 1.8GHz Intel Pentium 4 or AMD Athlon processor or equivalent/better 512 MB RAM 128 MB Video Card w/ DirectX 8 support (pixel and vertex shaders) DirectX7 compatible sound card 1.7GB of free hard drive space DirectX9.0c (included)";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game civIV = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, microprose.getUserId());
            microproseProducts.add(civIV);

            name = "Grand Theft Auto: Vice City";
            releaseDate = LocalDate.parse("2003-05-13", formatter);
            parentAdvisory = true;
            averageRating = 94;
            sales = 1830263;
            price = 10.50;
            description = "Welcome to Vice City. Welcome to the 1980s.From the decade of big hair excess and pastel suits comes a story of one mans rise to the top of the criminal pile. Vice City a huge urban sprawl ranging from the beach to the swamps and the glitz to the ghetto was one of the most varied complete and alive digital cities ever created. Combining open-world gameplay with a character driven narrative you arrive in a town brimming with delights and degradation and given the opportunity to take it over as you choose.Having just made it back onto the streets of Liberty City after a long stretch in maximum security Tommy Vercetti is sent to Vice City by his old boss Sonny Forelli. They were understandably nervous about his re-appearance in Liberty City so a trip down south seemed like a good idea. But all does not go smoothly upon his arrival in the glamorous hedonistic metropolis of Vice City. Hes set up and is left with no money and no merchandise. Sonny wants his money back but the biker gangs Cuban gangsters and corrupt politicians stand in his way. Most of Vice City seems to want Tommy dead. His only answer is to fight back and take over the city himself.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/12110/header.jpg?t=1457026681";
            computerRequirements = "Minimum:OS: Microsoft(r) Windows(r) 2000/XPProcessor: 800 MHz Intel Pentium III or 800 MHz AMD Athlon or 1.2GHz Intel Celeron or 1.2 GHz AMD Duron processorMemory: 128 MB of RAMGraphics: 32 MB video card with DirectX 9.0 compatible drivers (GeForce or better)DirectX Version: Microsoft DirectX(r) 9.0Hard Drive: 915 MB of free hard disk space (+ 635 MB if video card does NOT support DirectX Texture Compression)Sound Card: Sound Card with DirectX 9.0Recommended:Processor: Intel Pentium IV or AMD Athlon XP processor 256(+) MB of RAMMemory: 1.55 GB of free hard disk space(+ 635 MB if video card does NOT support DirectX Texture Compression)Graphics: 64(+) MB video card with DirectX 9.0 compatible drivers (GeForce 3 / Radeon 8500 or better with DirectX Texture Compression support)";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game gtavicecity = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, rockstar.getUserId());
            rockstarProducts.add(gtavicecity);


            name = "Call of Duty: Black Ops III";
            releaseDate = LocalDate.parse("2015-10-05", formatter);
            parentAdvisory = true;
            averageRating = 90;
            sales = 1378660;
            price = 60.00;
            description = "Treyarch developer of the two most-played games in Call of Duty(r) history returns with Call of Duty(r): Black Ops III. For the first time with three-years of development the revered award-winning studio has created its first title for next-gen hardware in the critically acclaimed Black Ops series. Welcome to Call of Duty: Black Ops 3 a dark twisted future where a new breed of Black Ops soldier emerges and the lines are blurred between our own humanity and the technology we created to stay ahead in a world where cutting-edge military robotics define warfare. Call of Duty: Black Ops 3 combines three unique game modes: Campaign Multiplayer and Zombies providing fans with the deepest and most ambitious Call of Duty ever. The Campaign has been designed as a co-op game that can be played with up to 4 players online or as a solo cinematic thrill-ride. Multiplayer will be the franchises deepest most rewarding and most engaging to date with new ways to rank up customize and gear up for battle. And Zombies delivers an all-new mind-blowing experience with its own dedicated narrative. Call of Duty: Black Ops 3 can be played entirely online and for the first time each of the offerings has its own unique player XP and progression systems. The title ushers in an unprecedented level of innovation including jaw-dropping environments never before experienced weaponry and abilities and the introduction of a new improved fluid movement system. All of this is brought to life by advanced technology custom crafted for this title including new AI and animation systems and graphics that redefine the standards Call of Duty fans have come to expect from the critically-acclaimed series with cutting edge lighting systems and visual effects.INTRODUCING A NEW ERA OF BLACK OPS:Call of Duty: Black Ops 3 deploys its players into a future where bio-technology has enabled a new breed of Black Ops soldier. Players are now always on and always connected to the intelligence grid and their fellow operatives during battle. In a world more divided than ever this elite squad consists of men and women who have enhanced their combat capabilities to fight faster stronger and smarter. Every soldier has to make difficult decisions and visit dark places in this engaging gritty narrative.A CALL OF DUTY CAMPAIGN UNLIKE ANYTHING BEFORE IT:Treyarch elevates the Call of Duty social gaming experience by delivering a campaign with the ability to play cooperatively with up to four players online using the same battle-tested network infrastructure and social systems that support its world-class Multiplayer and Zombies game modes. Designed for co-op and re-playability players encounter all the epic cinematic gameplay moments Call of Duty is known for delivering as well as new open-area arena-style gameplay elements designed to allow players to approach the game with a different strategy each time they play. And now every player is completely customizable: from weapons and loadouts to abilities and outfits all with full progression systems and a personalized armory to show off accomplishments providing a constantly-evolving campaign experience.PREPARE FOR A LEVELED-UP MULTIPLAYER:With Black Ops 3 Treyarch premieres a new momentum-based chained-movement system allowing players to fluidly move through the environment with finesse using controlled thrust jumps slides and mantling abilities in a multitude of combinations all while maintaining complete control over your weapon at all times. Maps are designed from the ground-up for the new movement system allowing players to be successful with traditional movement as well as advanced tactics and maneuvers. Black Ops 3 multiplayer also introduces the new Specialist character system which allows players to rank up and master each specific characters battle-hardened capabilities and weapons. With this addition to Traditional and Weapons XP progressions systems Black Ops 3 multiplayer gives players three different ways to rank up.FIGHT THE UNDEAD IN AN ALL-NEW HORROR STORY:No Treyarch title would be complete without its signature Zombies offering - a full-game experience with its own distinct storyline right out of the box. Black Ops 3 Zombies is the most immersive and ambitious Call of Duty Zombies to date with a full XP-based progression system for players that adds depth and re-playability to the engaging gameplay Zombies fans have come to expect.Call of Duty: Black Ops 3 delivers the ultimate 3-games-in-1 experience.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/311210/header.jpg?t=1473725004";
            computerRequirements = "Minimum:OS: Windows 7 64-Bit / Windows 8 64-Bit / Windows 8.1 64-BitProcessor: Intel(r) Core(tm) i3-530 @ 2.93 GHz / AMD Phenom(tm) II X4 810 @ 2.60 GHzMemory: 6 GB RAMGraphics: NVIDIA(r) GeForce(r) GTX 470 @ 1GB / ATI(r) Radeon(tm) HD 6970 @ 1GBDirectX: Version 11Network: Broadband Internet connectionStorage: 60 GB available spaceSound Card: DirectX Compatible";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            Game callofdutyblackops3 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, activision.getUserId());
            activisionProducts.add(callofdutyblackops3);

            name = "Half-Life";
            releaseDate = LocalDate.parse("1998-11-08", formatter);
            parentAdvisory = false;
            averageRating = 96;
            sales = 5927504;
            price = 10.00;
            description = "Named Game of the Year by over 50 publications Valves debut title blends action and adventure with award-winning technology to create a frighteningly realistic world where players must think to survive. Also includes an exciting multiplayer mode that allows you to play against friends and enemies around the world.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/70/header.jpg?t=1447890508";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game halflife = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(halflife);

            name = "Half-Life 2";
            releaseDate = LocalDate.parse("2004-11-16", formatter);
            parentAdvisory = false;
            averageRating = 96;
            sales = 9901173;
            price = 10.00;
            description = "Half-Life 2 opens the door to a world where the player's presence affects everything around him, from the physical environment to the behaviors even the emotions of both friends and enemies. The player again picks up the crowbar of research scientist Gordon Freeman, who finds himself on an alien-infested Earth being picked to the bone, its resources depleted, its populace dwindling. Freeman is thrust into the unenviable role of rescuing the world from the wrong he unleashed back at Black Mesa. And a lot of people he cares about are counting on him.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/220/header.jpg?t=1456860366";
            computerRequirements = "Minimum: 500 mhz processor 96mb ram 16mb video card Windows XP Mouse Keyboard Internet ConnectionRecommended: 800 mhz processor 128mb ram 32mb+ video card Windows XP Mouse Keyboard Internet Connection";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game halflife2 = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, valve.getUserId());
            valveProducts.add(halflife2);

            name = "Plants vs. Zombies: Game of the Year";
            releaseDate = LocalDate.parse("2009-05-06", formatter);
            parentAdvisory = false;
            averageRating = 87;
            sales = 1169180;
            price = 6.50;
            description = "Zombies are invading your home, and the only defense is your arsenal of plants! Armed with an alien nursery-worth of zombie-zapping plants like peashooters and cherry bombs, you'll need to think fast and plant faster to stop dozens of types of zombies dead in their tracks. Obstacles like a setting sun, creeping fog and a swimming pool add to the challenge, and with five game modes to dig into, the fun never dies!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/3590/header.jpg?t=1447351772";
            computerRequirements = "OS: Windows XP/Vista/7                    Processor: 1.2GHz+ processor                    Memory: 1GB of RAM                    Graphics: 128MB of video memory 16-bit or 32-bit color quality                    DirectX: DirectX 8 or later                    Hard Drive: 65+MB of free hard drive space                    Sound: DirectX-compatible sound";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityZombies.getTagId());
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game plantsvszombies = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements,
                            price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(plantsvszombies);

            name = "Darksiders Warmastered Edition";
            releaseDate = LocalDate.parse("2016-11-29", formatter);
            parentAdvisory = false;
            averageRating = 87;
            sales = 2548378;
            price = 20.00;
            description = "Deceived by the forces of evil into prematurely bringing about the end of the world, War – the first Horseman of the Apocalypse – stands accused of breaking the sacred law by inciting a war between Heaven and Hell.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/462780/header.jpg?t=1481150698";
            computerRequirements = "Minimum:OS: Windows 7 Windows 8 Windows 10Processor: Intel or AMD Dual Core CPUMemory: 4 GB RAMGraphics: DirectX 10 Feature Level AMD or NVIDIA Card with 1 GB VRAMDirectX: Version 11Storage: 36 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityRPG.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game darksidersWarmasteredEdition = gameSessionBeanLocal.createNewGame(
                    new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(darksidersWarmasteredEdition);

            name = "Killing Floor";
            releaseDate = LocalDate.parse("2009-05-14", formatter);
            parentAdvisory = true;
            averageRating = 72;
            sales = 3496958;
            price = 20.00;
            description = "Killing Floor is a Co-op Survival Horror FPS set in the devastated cities and countryside of England after a series of cloning experiments for the military goes horribly wrong. You and your friends are members of the military dropped into these locations with a simple mission: Survive long enough to cleanse the area of the failed experiments!Cooperative gameplay for up to six players against multiple waves of specimensPersistent Perks system allowing players to convert their in-game achievements into permanent improvements to their characters skills and abilities.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/1250/header.jpg?t=1464640213";
            computerRequirements = "Minimum:OS: Windows XP/Vista Processor: 1.2 GHZ or Equivalent Memory: 1 GB RAM Graphics: 64 MB DX9 Compliant Hard Drive: 2 GB free hard drive space Sound: DX 8.1 Compatible Audio";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityZombies.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityHorror.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game killingfloor = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(killingfloor);

            name = "RollerCoaster Tycoon 3: Platinum!";
            releaseDate = LocalDate.parse("2006-10-26", formatter);
            parentAdvisory = false;
            averageRating = 81;
            sales = 799182;
            price = 20.00;
            description = "Rollercoaster Tycoon 3 Platinum combines the excitement of rollercoasters with the fun of great strategy sim. RCT3 Platinum combines the roller coaster theme park fun of the Roller Coaster Tycoon 3 with included expansion packs Soaked! and Wild! Now enjoy more options than ever. Build your own water slide or create your own safari with real animals. Watch guest reactions to your ultimate theme park!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/2700/header.jpg?t=1473276734";
            computerRequirements = "Minimum: Supported OS: Windows 2000/XPProcessor: Pentium(r) III 733 MHz or compatible (Pentium(r) 4 1.2 GHz or compatible recommended)Memory: 128 MB RAM; 256 MB for XP (256 MB; 384 MB for XP recommended)Graphics: Any ATI Radeon(tm) or GeForce(tm) 2 with 32MB or higher; or other video card with 32MB and hardware T&L (ATI Radeon(tm) 64 MB SDR or GeForce(tm) 2 Pro or other video card with 64 MB or more memory and hardware T&L recommended)*DirectX Version: DirectX(r) version 9 (included) or higherSound: Windows(r) 2000/XP-compatible 16-bit sound card*Hard Drive: 600 MB free* Indicates device should be compatible with DirectX(r) version 9.0b or higher.";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySimulation.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game rollercoaster = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(rollercoaster);

            name = "Garry's Mod";
            releaseDate = LocalDate.parse("2006-11-29", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 12790674;
            price = 20.00;
            description = "Garrys Mod is a physics sandbox. There arent any predefined aims or goals. We give you the tools and leave you to play.You spawn objects and weld them together to create your own contraptions - whether thats a car a rocket a catapult or something that doesnt have a name yet - thats up to you. You can do it offline or join the thousands of players who play online each day. If youre not too great at construction - dont worry! You can place a variety of characters in silly positions.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/4000/header.jpg?t=1469897414";
            computerRequirements = "Minimum:OS: Windows(r) Vista/XPProcessor: 1.8 GHz ProcessorMemory: 2 GB RAMGraphics: DirectX(r) 9 level Graphics Card (Requires support for SSE)DirectX: Version 9.0cNetwork: Broadband Internet connectionStorage: 5 GB available spaceSound Card: DirectX(r) 9 compatibleAdditional Notes: Mouse Keyboard Monitor";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySimulation.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            Game garysmod = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(garysmod);

            

            name = "Call of Duty: Infinite Warfare";
            releaseDate = LocalDate.parse("2016-11-03", formatter);
            parentAdvisory = true;
            averageRating = 87;
            sales = 255932;
            price = 76.90;
            description = "Includes the Terminal Bonus Map and Zombies in Spaceland Pack contains a weapon camo calling card and a Fate and Fortune Card Pack! Infinite Warfare delivers three unique game modes: Campaign Multiplayer and Zombies.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/292730/header.jpg?t=1479411431";
            computerRequirements = "Minimum:OS: Windows 7 64-Bit or laterProcessor: Intel Core i3-3225 @ 3.30GHz or equivalentMemory: 8 GB RAMGraphics: NVIDIA GeForce GTX 660 2GB / AMD Radeon HD 7850 2GBDirectX: Version 11Network: Broadband Internet connectionStorage: 70 GB available spaceSound Card: DirectX 11 CompatibleAdditional Notes: Disk space requirement may change over time.";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            videoLink = "";
            tags = new ArrayList<>();
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            Game callofdutyinfinitewarfare = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, activision.getUserId());
            activisionProducts.add(callofdutyinfinitewarfare);

            name = "Project CARS - Pagani Edition";
            releaseDate = LocalDate.parse("2016-10-28", formatter);
            parentAdvisory = false;
            averageRating = 75;
            sales = 342378;
            price = 0.00;
            description = "'Project CARS – Pagani Edition' Features the Pagani Huayra, Huayra BC, Zonda Cinque, Zonda R, and Zonda Revolucion, and three intricately modeled tracks — the Nürburgring and Nordschleife combo, Monza GP, and Azure Coast — along with two game modes, and full VR support on both Oculus Rift and HTC Vive, as well as support for 4K screens and traditional displays.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/429180/header.jpg?t=1477731204";
            computerRequirements = "Minimum:OS: Windows Vista Windows 7 with latest Service Packs or laterProcessor: 2.66 GHz Intel Core 2 Quad Q8400 3.0 GHz AMD Phenom II X4 940Memory: 4 GB RAMGraphics: nVidia GTX 260 ATI Radeon HD 5770DirectX: Version 9.0Network: Broadband Internet connectionStorage: 25 GB available spaceSound Card: DirectX compatible sound card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityRacing.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntitySimulation.getTagId());
            Game projectcars = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(projectcars);

            name = "Soccer Manager 2017";
            releaseDate = LocalDate.parse("2016-09-16", formatter);
            parentAdvisory = false;
            averageRating = 65;
            sales = 217551;
            price = 10.50;
            description = "Soccer Manager 2017 - Play for Free Compete for Real.The most diverse FREE 2 PLAY football management simulator in the world. Take on a top flight club and test your management skills against the best or help a struggling lower division team fight for glory.Soccer Manager 2017 FeaturesReactive 2D Live Match EnvironmentMonitor your teams performance during live games. React with different tactics and strategies and watch your team adapt to your decisions in real time.Play Anywhere EverywhereOur cloud-based technology allows you to save your game on any device and continue to play on another. No need to set up a different account for different devices take your team with you anywhere and play anytime.Soccer Manager 2017 contains the following new improvements and features:Updated leagues and teams for 2016/17 season.Updated domestic and continental competitions.New user interface.New facilities upgrade.Player Spotlight.Player manager and team awards.So dust off your tracksuit and get ready to start your new managerial career on Soccer Manager 2017.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/510630/header.jpg?t=1474049541";
            computerRequirements = "Minimum:OS: Windows XP or LaterProcessor: 1 GHz or higherMemory: 1 GB RAMNetwork: Broadband Internet connectionStorage: 80 MB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySports.getTagId());
            tags.add(tagEntityStrategy.getTagId());
            tags.add(tagEntitySimulation.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityCasual.getTagId());
            Game soccermanager2017 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(soccermanager2017);

            name = "Paladins";
            releaseDate = LocalDate.parse("2016-09-15", formatter);
            parentAdvisory = false;
            averageRating = 83;
            sales = 5979874;
            price = 0.00;
            description = "Join 25+ million players in Paladins, the free-to-play fantasy team-based shooter sensation. Wield guns and magic as a legendary Champion of the Realm, customizing your core set of abilities to play exactly how you want to play.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/444090/header.jpg?t=1474591627";
            computerRequirements = "Minimum:OS: Windows XP SP2 Windows Vista or Windows 7Processor: Core 2 Duo 2.4 GHz or Althon X2 2.7 GHzMemory: 2 GB RAMGraphics: ATI or Nvidia graphics card with 512MB video ram or better and Shader Model 3.0+ support. (ATI Radeon 3870 or higher Nvidia GeForce 8800 GT or higher)Storage: 10 GB available spaceSound Card: DirectX compatible sound card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityMOBA.getTagId());
            Game paladins = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(paladins);

            name = "BioShock 2 Remastered";
            releaseDate = LocalDate.parse("2016-09-15", formatter);
            parentAdvisory = true;
            averageRating = 79;
            sales = 3219325;
            price = 20.00;
            description = "BioShock 2 provides players with the perfect blend of explosive first-person shooter combat and compelling award-winning storytelling. The halls of Rapture once again echo with sins of the past. Along the Atlantic coastline, a monster has been snatching little girls and bringing them back to the undersea city of Rapture. Players step into the boots of the most iconic denizen of Rapture, the Big Daddy, as they travel through the decrepit and beautiful fallen city, chasing an unseen foe in search of answers and their own survival.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/409720/header.jpg?t=1473983496";
            computerRequirements = "Minimum:OS: Windows 7 Service Pack 1 64-bit. Platform Update for Windows 7 SP1 and Windows Server 2008 R2 SP1Processor: Intel E6750 Core 2 Duo 2.66 GHz / AMD Athlon X2 2.7 GHZMemory: 4 GB RAMGraphics: 2GB AMD Radeon HD 7770 / 2GB NVIDIA GeForce GTX 670DirectX: Version 11Storage: 25 GB available spaceSound Card: DirectX Compatible Sound Device";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityFPS.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityRPG.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            Game bioshock2 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(bioshock2);

            name = "NBA 2K17";
            releaseDate = LocalDate.parse("2016-09-20", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 153165;
            price = 20.00;
            description = "Following the record-breaking launch of NBA 2K16, the NBA 2K franchise continues to stake its claim as the most authentic sports video game with NBA 2K17. As the franchise that “all sports video games should aspire to be” (GamesRadar), NBA 2K17 will take the game to new heights and continue to blur the lines between video game and reality.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/385760/header.jpg?t=1474391274";
            computerRequirements = "Minimum:OS: Windows 7 64-bit Windows 8.1 64-bit or Windows 10 64-bitProcessor: Intel(r) Core(tm) i3-530 @ 2.93 GHz / AMD Phenom(tm) II X4 805 @ 2.50 GHz or betterMemory: 4 GB RAMGraphics: NVIDIA(r) GeForce(r) GT 430 1GB / ATI(r) Radeon(tm) HD 6450 1GB or betterDirectX: Version 11Storage: 70 GB available spaceSound Card: DirectX 9.0x compatibleAdditional Notes: Dual-analog gamepad";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntitySports.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntitySimulation.getTagId());
            Game nba2k17 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(nba2k17);

            name = "Football Manager 2017";
            releaseDate = LocalDate.parse("2016-11-04", formatter);
            parentAdvisory = false;
            averageRating = 74;
            sales = 5791198;
            price = 50.00;
            description = "Take control of your favourite football team in Football Manager 2017 the most realistic and immersive football management game to date. Its the closest thing to doing the job for real!With over 2500 real clubs to manage and over 500000 real footballers and staff to sign Football Manager 2017 elevates you into a living breathing world of football management with you at the centre.  Youll have full control of transfers and decide who plays and who sits on the bench. Youre in complete control of tactics team-talks and pitch-side instructions and youll follow the match live with our acclaimed 3D match engine. Youll also deal with real football media solve player-happiness problems and the board will watch your every move.Fans who pre-purchase Football Manager 2017 on Steam will be rewarded with Football Manager Touch 2017 for free (on PC Mac or Linux) and as a thank you for pre-purchasing youll also get a range of free downloadable content for Touch including Board override No firing All job applications National management and 3 brand new challenges to play in the Challenge game mode. Touch is the more streamlined transfers and tactics way to manage and can be purchased separately as a standalone game.In addition youll also enjoy access to a fully-playable Beta version of the game which will be available roughly two weeks prior to the official release date. Single player careers started in this Beta version will continue in the full game.Loyal fans of the series can enjoy an exclusive Steam-only discount of five per cent off for every Football Manager game they own (PC Mac or Linux) dating back to Football Manager 2013. This means theres a maximum 20 per cent discount available to fans who own all of the last four versions.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/482730/header.jpg?t=1473937693";
            computerRequirements = "Minimum:OS: Windows Vista (SP2) 7 (SP1) 8 8.1 10 (1607) - 64-bit or 32-bitProcessor: Intel Pentium 4 Intel Core AMD Athlon 2.2GHz+Memory: 2 GB RAMGraphics: NVIDIA  GeForce 8600M GT AMD/ATI Mobility Radeon HD 2400 Intel GMA X3100 256MB VRAMDirectX: Version 9.0cStorage: 3 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntitySports.getTagId());
            tags.add(tagEntitySimulation.getTagId());
            Game footballmanager2017 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(footballmanager2017);

            name = "Titan Quest Anniversary Edition";
            releaseDate = LocalDate.parse("2016-08-31", formatter);
            parentAdvisory = false;
            averageRating = 74;
            sales = 1743816;
            price = 20.00;
            description = "For its 10 year anniversary, Titan Quest will shine in new splendour. This Anniversary Edition combines both Titan Quest and Titan Quest Immortal Throne in one game, and has been given a massive overhaul for the ultimate ARPG experience.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/475150/header.jpg?t=1473470071";
            computerRequirements = "Minimum:OS: Windows XP / Vista / 7 / 8 / 10 32 or 64 bitProcessor: 2.0 GHz CPUMemory: 1 GB RAMGraphics: 128 MB NVIDIA GeForce 6800 series or ATI Radeon X800 series or equivalentDirectX: Version 9.0cStorage: 5 GB available spaceSound Card: DirectX compatible";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySingleplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityRPG.getTagId());
            Game titanquest = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(titanquest);

            name = "H1Z1: King of the Kill";
            releaseDate = LocalDate.parse("2016-02-17", formatter);
            parentAdvisory = false;
            averageRating = 74;
            sales = 3186504;
            price = 20.00;
            description = "King of the Kill is a large-scale fight-to-the-death shooter where every moment counts. Drop into the high-intensity arena-style grudge match and activate your inner beast mode. Gear up fast throw together a game plan and well see if you have what it takes to be the last man standing. Rack up a kill streak or just add to the chaos - this is a spectacle and only one can be King of the Kill.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/439700/header.jpg?t=1460503021";
            computerRequirements = "Minimum:OS: Windows 7 SP1 64 bitProcessor: Intel i3 Dual-Core with Hyper-Threading (required)Memory: 4 GB RAMGraphics: nVidia GeForce GTX 275 series or higherDirectX: Version 10Network: Broadband Internet connectionStorage: 20 GB available spaceSound Card: DirectX Compatible Sound Card";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityAction.getTagId());
            Game h1z1 = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(h1z1);

            name = "BlackShot: Mercenary Warfare FPS";
            releaseDate = LocalDate.parse("2016-05-31", formatter);
            parentAdvisory = false;
            averageRating = 80;
            sales = 680094;
            price = 0.00;
            description = "BlackShotBlackShot is a free-to-play first-person shooter set in a post-apocalyptic world.Join other mercenaries in brutal team combat multiple game modes and experience in-depth customization to create your perfect warrior!StoryThe old world ended on the fourth of December 2033.In the preceding decade advances in human cloning had led to the development of the ultimate weapon; the expendable clone soldier the Mercenary.Nations began expanding their borders aggressively seeking more resources in order to expand their clone armies. The entire world soon became embroiled in global conflict. BlackShot is one the worlds elite mercenary groups. You are a newly contracted Mercenary. Will you rise through the ranks to become elite?Multiply Game ModesRunning Man Mode (RM)Team Death Match (TDM)Team Flag Match (TFM)Search & Destroy (SD)Protect the Commander (PC)Unlimited Battle Arena (UBA)Bunker Defense (BD)Mercenary Level & Rank SystemRise from lowly Recruit all the way up to Commander-in-ChiefReceive Weapons Gear and Tactics at every level!Gear SystemEnhance your mercenary with Gear to make them better stronger faster!Receive stat bonuses or increase your weapon load.Tactical SlotsAdd depth to your character customization by assigning weapons expertise.Unlock more slots as you gain ranks and create a deadly mercenary!Clan System & Dedicated Clan ServersCreate your own or join an existing clan customize your emblem and dominate!Clan rankings and Clan servers for team matches.WeaponsMassive array of weapons such as AR BR Snipers SMG Shotguns and many more.7 Mastery categories and multiple ranks that unlock unique weaponry!";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/433350/header.jpg?t=1471036012";
            computerRequirements = "Minimum:OS: Windows XP / 2000 / Vista / 7/ 8 /10Processor: Pentium 4 2.0GhzMemory: 1 GB RAMGraphics: GeForce 6800 GT / RADEON X800 GTDirectX: Version 9.0cNetwork: Broadband Internet connectionStorage: 2 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityFPS.getTagId());
            Game blackshot = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(blackshot);

            name = "ARK: Survival Of The Fittest";
            releaseDate = LocalDate.parse("2016-03-15", formatter);
            parentAdvisory = false;
            averageRating = 81;
            sales = 5995836;
            price = 42.00;
            description = "Welcome to ARK: Survival of the Fittest, the first ever M.O.S.A. - a Multiplayer Online Survival Arena - that Studio Wildcard designed for the burgeoning wild west of eSports. A spin-off from the most popular open-world Early Access game on Steam ARK: Survival Evolved, ARK: Survival of the Fittest (SotF) pits up to 72 combatants in an action-packed struggle for survival where players are ultimately pushed into an epic final showdown where only one “Tribe” will make it out alive.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/407530/header.jpg?t=1471325195";
            computerRequirements = "Minimum:OS: 64-Bit Windows 7 Service Pack 1 or Windows 8/10Processor: 2 GHz Dual-Core 64-bit CPUMemory: 4000 MB RAMGraphics: GTX 500 or Above DirectX10 Compatible GPU with 2 GB or More Video RAMDirectX: Version 10Storage: 37000 MB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityMultiplayer.getTagId());
            tags.add(tagEntityOpenworld.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            Game arksurvivalofthefittest = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(arksurvivalofthefittest);

            name = "Undertale";
            releaseDate = LocalDate.parse("2015-09-15", formatter);
            parentAdvisory = false;
            averageRating = 92;
            sales = 2105743;
            price = 10.50;
            description = "Welcome to UNDERTALE. In this RPG, you control a human who falls underground into the world of monsters. Now you must find your way out... or stay trapped forever.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/391540/header.jpg?t=1468759045";
            computerRequirements = "Minimum:OS: Windows XP Vista 7 8 or 10Memory: 2 GB RAMGraphics: 128MBStorage: 200 MB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityFunny.getTagId());
            tags.add(tagEntityRPG.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game undertale = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(undertale);

            name = "Rise of the Tomb Raider";
            releaseDate = LocalDate.parse("2016-01-18", formatter);
            parentAdvisory = true;
            averageRating = 86;
            sales = 1186398;
            price = 60.00;
            description = "After uncovering an ancient mystery Lara must explore the most treacherous and remote regions of Siberia to find the secret of immortality before a ruthless organization known as Trinity. Lara must use her wits and survival skills form new alliances and ultimately embrace her destiny as the Tomb Raider. Experience high-octane action moments conquer beautifully hostile environments engage in brutal guerilla combat and explore awe-inspiring deadly tombs in the evolution of survival action. In Rise of the Tomb Raider Lara becomes more than a survivor as she embarks on her first Tomb Raiding expedition.Key Features:Laras Journey - Lara uncovers an ancient mystery that places her in the cross-hairs of a ruthless organization known as Trinity. As she races to find the secret before Trinity the trail leads to a myth about the Lost City of Kitezh. Lara knows she must reach the Lost City and its hidden secrets before Trinity. With that she sets out for Siberia on her first Tomb Raiding expedition.Woman vs. Wild - In Rise of the Tomb Raider Lara battles with not only enemies from around the world but the world itself. Hunt animals to craft weapons and scavenge for rare resources in densely populated ecosystems. Youll encounter beautifully hostile environments full of treacherous conditions and unstable landscapes that will require Lara to push her limits to the very edge. Guerilla Combat - Use the environment to your advantage scale trees and dive underwater to avoid or takedown enemies configure Laras gear weapons and ammo to suit your play style from stealth to guns blazing craft explosives on the fly to sow chaos and wield Laras signature combat bows and climbing axe. Return to Tomb Raiding - Tombs are back and theyre bigger and better than ever. In Rise of the Tomb Raider youll explore huge awe-inspiring ancient spaces littered with deadly traps solve dramatic environmental puzzles and decipher ancient texts to reveal crypts as you take on a world filled with secrets to discover.";
            headerImage = "http://cdn.akamai.steamstatic.com/steam/apps/391220/header.jpg?t=1473961644";
            computerRequirements = "Minimum:OS: Windows 7 64bitProcessor: Intel Core i3-2100 or AMD equivalentMemory: 6 GB RAMGraphics: NVIDIA GTX 650 2GB or AMD HD7770 2GBDirectX: Version 11Storage: 25 GB available space";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            tags.add(tagEntityAdventure.getTagId());
            tags.add(tagEntityAction.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game riseofthetombraider = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(riseofthetombraider);
            
            name = "BUILD: Ultimate Sandbox Building Game";
            releaseDate = LocalDate.parse("2019-08-31", formatter);
            parentAdvisory = false;
            averageRating = 90;
            sales = 3286398;
            price = 14.50;
            description = "Have you ever wanted to just build your own little fantasy world, a farm, some kind of castle, or just an epic fantasy city, without worrying about managing your economy, maintaining the happiness of your people, or gaining enough XP to unlock a building? BUILD is the game for you! Build & design your own fantasy world using over 600+ items, in this relaxing sandbox building game. No missions, no objectives, no timers. Just stress-free building, and good vibes. Stack, connect, or overlap objects to create unique combinations. Build whatever you like, whenever you like, however you like. The possibilities are endless!";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/1077530/header.jpg?t=1588083088";
            computerRequirements = "Minimum:OS: Windows 7 64bit Processor: Intel Core i3 or similar";
            videoLink = "";
            categoryid = categoryEntitySoftwareGame.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntitySimulation.getTagId());
            tags.add(tagEntityCasual.getTagId());
            tags.add(tagEntitySingleplayer.getTagId());
            Game build = gameSessionBeanLocal.createNewGame(new Game(parentAdvisory, headerImage, videoLink, name, description, computerRequirements, price, averageRating, releaseDate, sales),
                    categoryid, tags, company1.getUserId());
            company1Products.add(build);

            //Useless tag lists
            List<Long> tagIdsPopular = new ArrayList<>();
            tagIdsPopular.add(tagEntityPopular.getTagId());

            List<Long> tagIdsDiscount = new ArrayList<>();
            tagIdsDiscount.add(tagEntityDiscount.getTagId());

            List<Long> tagIdsPopularDiscount = new ArrayList<>();
            tagIdsPopularDiscount.add(tagEntityPopular.getTagId());
            tagIdsPopularDiscount.add(tagEntityDiscount.getTagId());

            List<Long> tagIdsPopularNew = new ArrayList<>();
            tagIdsPopularNew.add(tagEntityPopular.getTagId());
            tagIdsPopularNew.add(tagEntityNew.getTagId());

            List<Long> tagIdsPopularDiscountNew = new ArrayList<>();
            tagIdsPopularDiscountNew.add(tagEntityPopular.getTagId());
            tagIdsPopularDiscountNew.add(tagEntityDiscount.getTagId());
            tagIdsPopularDiscountNew.add(tagEntityNew.getTagId());

            List<Long> tagIdsEmpty = new ArrayList<>();

            //Hardware
            name = "Alienware 15";
            String warrantyDescription = "1 Year";
            String technicalSpecification = "";
            String manufacturingCountry = "China";
            releaseDate = LocalDate.parse("2018-10-25", formatter);
            averageRating = 86;
            sales = 86398;
            price = 3499.00;
            description = "Alienware’s thinnest 15 inch laptop ever. With optional hyper-efficient 8-phase voltage regulation, Cryo-Tech cooling v3.0 & new Legend industrial design. ";
            headerImage = "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/notebooks/alienware_notebooks/alienware_m15_r2/pdp/laptops-aw-alienware-m15-r2-nt-pdp-mod-hero.jpg?fmt=jpg&wid=570&hei=400";
            videoLink = "https://r2---sn-npoe7n7z.googlevideo.com/videoplayback?expire=1587579984&ei=8DegXoT0F8fg7QS_qYbgBQ&ip=95.85.80.135&id=o-AOysPbEWJYSEjIcaRmOTs1xwaW25YsAbyO9k3szwkHDG&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=59.977&lmt=1579252483939415&fvip=14&fexp=23882513&c=WEB&txp=5432432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRAIgP4SS2d6vcIaYpvr2G_RvCKZmxcUqQSqrp5cAO7VHXAECIDT1zht4TxgJxN4rYuY-OYyF60Av5-pBBLGU0MvVkxwT&rm=sn-ug5onuxaxjvh-n8vz7l,sn-n8vreez&req_id=2a38a94c6da2a3ee&ipbypass=yes&redirect_counter=3&cm2rm=sn-nposk7l&cms_redirect=yes&mh=qZ&mip=137.132.119.2&mm=34&mn=sn-npoe7n7z&ms=ltu&mt=1587558322&mv=m&mvi=1&pl=17&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=ALrAebAwRAIgMsPoPlkvdojYoXQHh2qb4oS-FnYziCv_6LwMSS3-S7kCIHyUHQa6sckBwNrYlkJ94diArP3ys4Dnza20xOa3-L8a";
            categoryid = categoryEntityLaptop.getCategoryId();
            tags = new ArrayList<>();
            Hardware alienware15 = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,20000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(alienware15);

            name = "Alienware 17";
            warrantyDescription = "1 Year";
            technicalSpecification = "";
            manufacturingCountry = "China";
            releaseDate = LocalDate.parse("2018-10-25", formatter);
            averageRating = 86;
            sales = 58748;
            price = 3999.00;
            description = "Alienware’s thinnest 17 inch laptop ever. With optional hyper-efficient 8-phase voltage regulation, Cryo-Tech cooling v3.0 & new Legend industrial design.";
            headerImage = "https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/notebooks/alienware_notebooks/alienware_17_r5_non_touch/spi/ng/notebook-alienware-17-r5-campaign-hero-504x350-ng.psd?fmt=png-alpha&wid=570&hei=400";
            videoLink = "https://r3---sn-npoeen76.googlevideo.com/videoplayback?expire=1587580127&ei=fzigXrL1NIn0yQXEyb9I&ip=95.85.80.135&id=o-AAdaSi6A_EjrDBOF7yKu5vqsKL8peT4Br8ITgXF9qbQf&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=47.484&lmt=1545221324245390&fvip=14&fexp=23882513&c=WEB&txp=2311222&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRQIhAK0rjZWS07OkNc3n-QgfEsoHrPeWsg1M-2n7VvTqTVM2AiAvjFywoDbV-Ocb3vc1G2e7fpi6kTkK3_p6yA722eanGQ%3D%3D&rm=sn-ug5onuxaxjvh-n8vz7r,sn-n8vrz7e&req_id=12ba22083e72a3ee&ipbypass=yes&redirect_counter=3&cm2rm=sn-npozs7s&cms_redirect=yes&mh=O2&mip=137.132.119.2&mm=34&mn=sn-npoeen76&ms=ltu&mt=1587558503&mv=m&mvi=2&pl=17&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=ALrAebAwRAIgLCDEL6VSVVbLA63A9bxnRZ3Loqg2EiirNcmgQlfGqx4CIDJZ_Lx3eU6ed-yg35b0lukdWevYvFNGaqgUBid-1Od_";
            categoryid = categoryEntityLaptop.getCategoryId();
            tags = new ArrayList<>();
            Hardware alienware17 = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,20000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(alienware17);

            name = "Razer Viper Ultimate Mouse";
            warrantyDescription = "2 Years";
            technicalSpecification = "";
            manufacturingCountry = "China";
            releaseDate = LocalDate.parse("2019-12-02", formatter);
            averageRating = 90;
            sales = 358748;
            price = 199.00;
            description = "Razer's high speed wireless gaming mouse";
            headerImage = "https://i.ytimg.com/vi/96G5y_znKr0/maxresdefault.jpg";
            videoLink = "https://r3---sn-npoe7ned.googlevideo.com/videoplayback?expire=1587580664&ei=mDqgXsWZK-K47ASm7YGoDQ&ip=95.85.80.135&id=o-ACWupvaGHYYkbYJnJ6NxmNYXr5JoSiTcscO9BCUXPpq5&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=60.046&lmt=1571879623143857&fvip=10&c=WEB&txp=5432432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRgIhALVwhuqmihzF60q_7UsbA5RzrQTggm0wd1t_Qyq91YblAiEAwRQi4S7DdQSkUnZeJ6us5dhJBWuvU6Wb4W7pL9_oTJU%3D&rm=sn-ug5onuxaxjvh-n8vz7s,sn-n8vyse7&req_id=2f180d9b3120a3ee&redirect_counter=2&cms_redirect=yes&ipbypass=yes&mh=1p&mip=137.132.119.2&mm=30&mn=sn-npoe7ned&ms=nxu&mt=1587558854&mv=u&mvi=2&pl=17&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRAIgBuaDX2oZugSeYSbZ2VfAbh4HQMxZEAnHc_oN6zpSGXYCIGc9sOqr51B2eo5ul4QDJkhxbTMvE-WzLf_RqNDj900w";
            categoryid = categoryEntityMouse.getCategoryId();
            tags = new ArrayList<>();
            Hardware viperultimate = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,50000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(viperultimate);
            
            name = "Razer Huntsman Elite";
            warrantyDescription = "2 Years";
            technicalSpecification = "";
            manufacturingCountry = "China";
            releaseDate = LocalDate.parse("2018-06-02", formatter);
            averageRating = 91;
            sales = 68448;
            price = 339.90;
            description = "Razer's best selling gaming keyboard";
            headerImage = "https://assets2.razerzone.com/images/og-image/razer-huntsman-elite-OGimage-1200x630.jpg";
            videoLink = "https://r1---sn-25glen7l.googlevideo.com/videoplayback?expire=1588218338&ei=gvWpXpnnH9mP1gLl3rKoCg&ip=83.171.253.36&id=o-AJJNl1oj4mnyprjaJUyKSC1Dfn1u-8XcLymjooqtePJ-&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=43.142&lmt=1542305278979237&fvip=6&c=WEB&txp=5531432&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRgIhANQtEBTpJRiOS1LoL5fnsTrz4XUp2etL_yBu8hRc4EWQAiEAh0NW9Xva-07wv1eyoLzVplqqCzfbAPN4JTwgwlLc-aE%3D&redirect_counter=1&rm=sn-5hndy7d&req_id=f63cc86293eaa3ee&cms_redirect=yes&ipbypass=yes&mh=Hw&mip=137.132.119.2&mm=31&mn=sn-25glen7l&ms=au&mt=1588196337&mv=D&mvi=5&pl=0&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=ALrAebAwRAIgQKKIOFGfViykB0ofQpmmROJTQdgw1glGADVfadlm4EkCIDsEYLOPyj18yk1PbC-6QcROs-5aXU9W4Ih214xtZH80&ir=1&rr=12";
            categoryid = categoryEntityKeyboard.getCategoryId();
            tags = new ArrayList<>();
            Hardware huntsmanelite = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,50000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(huntsmanelite);
            
            name = "ROG Swift PG27UQ Gaming Monitor";
            warrantyDescription = "2 Years";
            technicalSpecification = "";
            manufacturingCountry = "US";
            releaseDate = LocalDate.parse("2019-06-02", formatter);
            averageRating = 91;
            sales = 258448;
            price = 1999.00;
            description = "Asus 27 inch 4K UHD 144Hz Gaming Monitor";
            headerImage = "https://dlcdnimgs.asus.com/websites/global/products/fRoiTmXJNO2QtbzD/images/hero.jpg";
            videoLink = "https://r3---sn-npoeen7y.googlevideo.com/videoplayback?expire=1588218729&ei=CfepXsXZM4aj1gLf6qCQDA&ip=83.171.253.36&id=o-ALpb2Pxf2aCURbcqLv2KVPtCdHKSxzPxxu8dl0bQ_OwO&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=40.402&lmt=1527242326367280&fvip=3&c=WEB&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRgIhAI7mWkT6G6xlKhtc5prFSh7uiDLsg0lourpoDt0OqS7iAiEA6o4He-x14A2nXRMERGCsQZT1OHdmo13hrSQy65oh9GY%3D&redirect_counter=1&cm2rm=sn-5hneer7s&req_id=a3123fae2b7da3ee&cms_redirect=yes&mh=Wi&mip=137.132.119.2&mm=34&mn=sn-npoeen7y&ms=ltu&mt=1588196832&mv=u&mvi=2&pl=17&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=ALrAebAwRQIhAM6HQD2JFaaZb04y2rFdli_T_5Hu3AW9jkId2YD_sgL5AiBrL0a-ChSG6VUDTjrmkGHPINDZgEJT4tMN_4yt6O1T9A%3D%3D";
            categoryid = categoryEntityMonitor.getCategoryId();
            tags = new ArrayList<>();
            Hardware rogpg27UQ = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,50000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(rogpg27UQ);
            
            name = "GEFORCE RTX 2080 Ti";
            warrantyDescription = "2 Years";
            technicalSpecification = "";
            manufacturingCountry = "US";
            releaseDate = LocalDate.parse("2019-09-20", formatter);
            averageRating = 91;
            sales = 168448;
            price = 999.00;
            description = "Nvidia Highest Performance Graphics Card";
            headerImage = "https://images.hothardware.com/contentimages/newsitem/49936/content/GeForce_RTX_2080_Ti.jpg";
            videoLink = "https://r1---sn-npoeenez.googlevideo.com/videoplayback?expire=1588219117&ei=jfipXoSoGJHR1gLwxJWQBw&ip=83.171.253.36&id=o-ABZArSNbz55wqJ78Z4wIQ72kNImETl5arrUlpIW3qFVC&itag=22&source=youtube&requiressl=yes&vprv=1&mime=video%2Fmp4&ratebypass=yes&dur=57.515&lmt=1534790890195841&fvip=1&fexp=23882513&c=WEB&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cvprv%2Cmime%2Cratebypass%2Cdur%2Clmt&sig=AJpPlLswRQIhALjdfCc-HUD2gWjAOdyOHUJsOwk1IYhm31TiyORIhVm_AiAS-80XN7H6d5XC8gNUtrrrpfIzE2Pw9c1vKYIQ7VTOsw%3D%3D&redirect_counter=1&cm2rm=sn-5hnee67e&req_id=83c8f33d49b3a3ee&cms_redirect=yes&mh=Ph&mip=137.132.119.2&mm=34&mn=sn-npoeenez&ms=ltu&mt=1588197219&mv=u&mvi=0&pl=17&lsparams=mh,mip,mm,mn,ms,mv,mvi,pl&lsig=ALrAebAwRgIhAKHxAymK9Qsf3HvgxrmyZHNFi_ALuCdapfLwAjwusTa8AiEA0_rfqNK8nZfZf0X6YxLEzujRcCxUTrkLAKPSuiF4El0%3D";
            categoryid = categoryEntityGraphicscard.getCategoryId();
            tags = new ArrayList<>();
            Hardware rtx2080 = hardwareSessionBeanLocal.createNewHardware(new Hardware(warrantyDescription, technicalSpecification, manufacturingCountry,
                    name, description, price, averageRating, releaseDate, sales, headerImage, videoLink,50000),
                    categoryid, tags, company1.getUserId());
            company1Products.add(rtx2080);

            //Software
            name = "Fences";
            releaseDate = LocalDate.parse("2017-03-30", formatter);
            averageRating = 80;
            sales = 221325;
            price = 10.00;
            description = "Take control of your cluttered desktop and make way for a clean creative workspace with Fences, the perfect Windows organizational solution. Fences has a little something for everyone: it collects your icons, files, and folders in shaded areas that can be hidden with a simple double-click, and even creates desktop pages you can swipe through for easy sorting.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/607380/header.jpg?t=1572968167";
            computerRequirements = "Minimum:OS: Windows 10 / 8 / 7. Additional Notes: Administrator privileges are required for initial install. Windows 10 Creator's Update Multi-monitor DPI settings are not supported in this build. This feature will be supported when Creator's Update officially launches.";
            videoLink = "";
            categoryid = categoryEntityDesign.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware fences = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description,
                    computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(fences);

            name = "Wallpaper Engine";
            releaseDate = LocalDate.parse("2018-11-17", formatter);
            averageRating = 85;
            sales = 121337;
            price = 4.50;
            description = "Wallpaper Engine enables you to use live wallpapers on your Windows desktop. Various types of animated wallpapers are supported, including 3D and 2D animations, websites, videos and even certain applications. Choose an existing wallpaper or create your own and share it on the Steam Workshop!";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/431960/header.jpg?t=1581697699";
            computerRequirements = "Minimum:OS: Windows 7 (with Aero), 8.1, 10. Processor: 1.66 GHz Intel i5 or equivalent.";
            videoLink = "";
            categoryid = categoryEntityDesign.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware wallpaperengine = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description,
                    computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(wallpaperengine);

            name = "Plan V";
            releaseDate = LocalDate.parse("2020-04-22", formatter);
            averageRating = 0;
            sales = 0;
            price = 0.00;
            description = "From hobbyists to production professionals, Plan V is a virtual visualisation software. Plan V gives the user a sense of scale and immersion previously unattainable with traditional pre-visualisation tools. The software has been developed for Windows desktop platforms, with a Mac application coming soon.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/1259150/header.jpg?t=1587549692";
            computerRequirements = "Minimum:OS: Windows 10 64 bit. Processor: i5. Memory: 8 GB RAM. Graphics: Nvidia 970";
            videoLink = "";
            categoryid = categoryEntityVideoProduction.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityUpcoming.getTagId());
            OtherSoftware planV = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description,
                    computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(planV);

            name = "FaceRig";
            releaseDate = LocalDate.parse("2015-07-07", formatter);
            averageRating = 89;
            sales = 265802;
            price = 15.00;
            description = "FaceRig is a program enabling anyone with a webcam to digitally embody awesome characters. It is meant to be an open creation platform so everybody can make their own characters, backgrounds or props and import them into FaceRig.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/274920/header.jpg?t=1560949723";
            computerRequirements = "Windows® 7 or later. Intel® Core™ i3-3220 or equivalent. Memory: 2 GB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/2040621/movie480.webm?t=1447376992";
            categoryid = categoryEntityAnimationModeling.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware facerig = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(facerig);

            name = "Aseprite";
            releaseDate = LocalDate.parse("2016-02-22", formatter);
            averageRating = 83;
            sales = 213267;
            price = 14.50;
            description = "Aseprite is a pixel art tool that lets you create 2D animations for videogames.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/431730/header.jpg?t=1548704961";
            computerRequirements = "Windows Vista, 7, 8, or 10. Memory: 128 MB RAM. Storage: 40 MB available space";
            videoLink = "";
            categoryid = categoryEntityDesign.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware aseprite = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description,
                    computerRequirements, price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(aseprite);

            name = "PC building simulator";
            releaseDate = LocalDate.parse("2019-01-30", formatter);
            averageRating = 87;
            sales = 650802;
            price = 18.50;
            description = "Build your very own PC empire, from simple diagnosis and repairs to bespoke, boutique creations that would be the envy of any enthusiast. With an ever-expanding marketplace full of real-world components you can finally stop dreaming of that ultimate PC and get out there, build it and see how it benchmarks in 3DMark!";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/621060/header.jpg?t=1585392007";
            computerRequirements = "Windows 7 or higher. Intel® Core™ i3-3220 or equivalent. Memory: 2 GB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256741664/movie480.webm?t=1559904538";
            categoryid = categoryEntityEducation.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware pcbuilding = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(pcbuilding);

            name = "GameGuru";
            releaseDate = LocalDate.parse("2015-05-20", formatter);
            averageRating = 87;
            sales = 1050802;
            price = 18.50;
            description = "Welcome to GameGuru, game making for everyone. Our mission is to create the easiest and most enjoyable game creator. Join us and our great & enthusiastic community on this journey. Imagine creating a world that you and your friends come together and play... and creating it could be a matter of minutes with no technical jiggery-pokery needed at all! Of course, you can happily spend hours, days..even weeks creating your masterpiece, the great thing about GameGuru is, you won't be held up by learning strange alien languages or using complex modeling tools. The time you spend with GameGuru is all about creating and having fun whilst doing it.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/266310/header.jpg?t=1570630243";
            computerRequirements = "Windows 7 or higher. Intel® Core™ i3-3220 or equivalent. Memory: 2 GB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/2036980/movie480.webm?t=1523977582";
            categoryid = categoryEntityGameDev.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware gameguru = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(gameguru);

            name = "GameMaker Studio 2";
            releaseDate = LocalDate.parse("2017-03-09", formatter);
            averageRating = 92;
            sales = 150802;
            price = 85.00;
            description = "GameMaker Studio 2 is the latest and greatest incarnation of GameMaker! It has everything you need to take your idea from concept to finished game. With a fresh user interface and many new exciting features including; Real-Time Animation Editing, a new innovative workflow and seamless path from Drag and Drop to actual code, developing top quality games has never been easier!";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/585410/header.jpg?t=1576156017";
            computerRequirements = "Windows 7 or higher. Intel® Core™ i3-3220 or equivalent. Memory: 2 GB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256678566/movie480.webm?t=1488983374";
            categoryid = categoryEntityGameDev.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware gamemakerstudio = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(gamemakerstudio);

            name = "Soundpad";
            releaseDate = LocalDate.parse("2016-11-09", formatter);
            averageRating = 85;
            sales = 729680;
            price = 5.00;
            description = "Play sounds in voice chats in high digital quality. You probably heard of soundboard, where people put sounds together like the popular Duke Nukem sounds. With Soundpad you can play them not only to yourself, but also to others.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/629520/header.jpg?t=1577013777";
            computerRequirements = "Windows 7 or higher. Intel or AMD with 1 GHz or faster. Memory: 2 GB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256695572/movie480.webm?t=1506794128";
            categoryid = categoryEntityAudio.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware soundpad = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(soundpad);

            name = "openCanvas 7";
            releaseDate = LocalDate.parse("2017-09-21", formatter);
            averageRating = 84;
            sales = 230677;
            price = 80.00;
            description = "openCanvas is a painting software dedicated to Windows Operating System, that is suitable to beginners to advanced users.Equipped with analog-like brush strokes and functional yet user-friendly interface, openCanvas has the higher basic performance and the unique function that enables to record and replay the drawing procedure.Various functions and excellent performance of openCanvas strongly support visualizing your creativity.";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/398810/header.jpg?t=1562579671";
            computerRequirements = "OS: Windows 7 or higher. Processor: x86 compatible processor supporting SSE2. Memory: 500 MB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "https://steamcdn-a.akamaihd.net/steam/apps/256733576/movie480.webm?t=1540800060";
            categoryid = categoryEntityPhotoEditing.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware opencanvas7 = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(opencanvas7);

            name = "Stream Avatars";
            releaseDate = LocalDate.parse("2017-07-26", formatter);
            averageRating = 82;
            sales = 130647;
            price = 14.50;
            description = "Stream Avatars is an interactive overlay for live broadcasters on Twitch, Mixer, and Youtube. Your viewers can knock each other off the screen with punches, bombs, or a duel show match where the winner takes all!";
            headerImage = "https://steamcdn-a.akamaihd.net/steam/apps/665300/header.jpg?t=1576402488";
            computerRequirements = "OS: Windows 7 or higher. Processor: x86 compatible processor supporting SSE2. Memory: 500 MB RAM. Graphics: NVIDIA GeForce GT220 or equivalent.";
            videoLink = "";
            categoryid = categoryEntityPhotoEditing.getCategoryId();
            tags = new ArrayList<>();
            tags.add(tagEntityPopular.getTagId());
            OtherSoftware streamAvatars = otherSoftwareSessionBeanLocal.createNewOtherSoftware(new OtherSoftware(name, description, computerRequirements,
                    price, averageRating, releaseDate, sales, headerImage, videoLink),
                    categoryid, tags, company1.getUserId());
            company1Products.add(streamAvatars);

            //SaleTransaction
            List<SaleTransactionLineItem> saleTransactionLineItems = new ArrayList<>();
            
            
            SaleTransactionLineItem saleTransactionLineItem = new SaleTransactionLineItem(streamAvatars, 1, new BigDecimal("14.50"), 
                    new BigDecimal("14.50"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            Long newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer1.getUserId(), 
                    new SaleTransaction(1, 1, new BigDecimal("14.50"), LocalDateTime.of(2020,Month.FEBRUARY,3,6,30,40), saleTransactionLineItems, false));
            
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(portal, 1, new BigDecimal("10.00"), 
                    new BigDecimal("10.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(dota2, 1, new BigDecimal("0.00"), 
                    new BigDecimal("0.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(civVI, 1, new BigDecimal("74.90"), 
                    new BigDecimal("74.90"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer1.getUserId(), 
                    new SaleTransaction(3, 3, new BigDecimal("84.90"), LocalDateTime.of(2020,Month.FEBRUARY,3,23,8,42), saleTransactionLineItems, false));

            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(gtaV, 1, new BigDecimal("40.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            Long newSaleTransactionId3 = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer1.getUserId(), 
                    new SaleTransaction(1, 1, new BigDecimal("40.00"), LocalDateTime.of(2020,Month.FEBRUARY,23,17,30,50), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(gtaV, 1, new BigDecimal("40.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer2.getUserId(), 
                    new SaleTransaction(1, 1, new BigDecimal("40.00"), LocalDateTime.of(2020,Month.MARCH,3,1,38,48), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(watchdogs2, 2, new BigDecimal("70.00"), 
                    new BigDecimal("140.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer2.getUserId(), 
                    new SaleTransaction(1, 2, new BigDecimal("140.00"), LocalDateTime.of(2020,Month.MARCH,13,10,17,40), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(watchdogs2, 2, new BigDecimal("70.00"), 
                    new BigDecimal("140.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer3.getUserId(), 
                    new SaleTransaction(1, 2, new BigDecimal("140.00"), LocalDateTime.of(2020,Month.MARCH,13,10,17,40), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(gtaV, 1, new BigDecimal("40.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer1.getUserId(), 
                    new SaleTransaction(1, 1, new BigDecimal("40.00"), LocalDateTime.of(2020,Month.APRIL,3,1,38,48), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(watchdogs2, 1, new BigDecimal("76.90"), 
                    new BigDecimal("76.90"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(nba2k17, 2, new BigDecimal("20.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(elderscrollsV, 3, new BigDecimal("54.00"), 
                    new BigDecimal("162.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer1.getUserId(), 
                    new SaleTransaction(3, 6, new BigDecimal("278.9"), LocalDateTime.of(2020,Month.APRIL,4,8,24,40), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(watchdogs2, 1, new BigDecimal("76.90"), 
                    new BigDecimal("76.90"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(nba2k17, 2, new BigDecimal("20.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(elderscrollsV, 3, new BigDecimal("54.00"), 
                    new BigDecimal("162.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer2.getUserId(), 
                    new SaleTransaction(3, 6, new BigDecimal("278.9"), LocalDateTime.of(2020,Month.APRIL,4,8,24,40), saleTransactionLineItems, false));
            
            saleTransactionLineItems = new ArrayList<>();
            saleTransactionLineItem = new SaleTransactionLineItem(watchdogs2, 1, new BigDecimal("76.90"), 
                    new BigDecimal("76.90"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(nba2k17, 2, new BigDecimal("20.00"), 
                    new BigDecimal("40.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            saleTransactionLineItem = new SaleTransactionLineItem(elderscrollsV, 3, new BigDecimal("54.00"), 
                    new BigDecimal("162.00"));
            saleTransactionLineItems.add(saleTransactionLineItem);
            newSaleTransactionId = saleTransactionSessionBeanLocal.createNewSaleTransaction(customer3.getUserId(), 
                    new SaleTransaction(3, 6, new BigDecimal("278.9"), LocalDateTime.of(2020,Month.APRIL,4,8,24,40), saleTransactionLineItems, false));
            
            //Promotion
            Promotion promo1 = promotionSessionBean.createPromotion(new Promotion("VALVE SALE",
                    "YAAAY ANOTHER SALLLLEE", (double) 10, (double) 0, LocalDateTime.now(),
                    LocalDateTime.now().plusDays(10), new ArrayList<>(Arrays.asList(csgo))));

            killingfloor.getPromotions().add(promo1);
            try {
                tags = new ArrayList<>();
                tags.add(tagEntityZombies.getTagId());
                tags.add(tagEntityFPS.getTagId());
                tags.add(tagEntityHorror.getTagId());
                tags.add(tagEntitySingleplayer.getTagId());
                tags.add(tagEntityMultiplayer.getTagId());
                tags.add(tagEntityDiscount.getTagId());
                gameSessionBeanLocal.updateGame(killingfloor, killingfloor.getCategory().getCategoryId(), tags);
            } catch (CategoryNotFoundException | ProductNotFoundException | TagNotFoundException | UpdateProductException ex) {
                ex.printStackTrace();
            }

            //Company set products
            company1.setProducts(company1Products);
            companySessionBeanLocal.updateCompany(company1);
            valve.setProducts(valveProducts);
            companySessionBeanLocal.updateCompany(valve);
            activision.setProducts(activisionProducts);
            companySessionBeanLocal.updateCompany(activision);
            microprose.setProducts(microproseProducts);
            companySessionBeanLocal.updateCompany(microprose);
            rockstar.setProducts(rockstarProducts);
            companySessionBeanLocal.updateCompany(rockstar);

        } catch (SystemAdminUsernameExistException | UnknownPersistenceException | InputDataValidationException
                | CreateNewCategoryException | CreateNewTagException | CreateNewProductException
                | ProductSkuCodeExistException | CompanyNotFoundException
                | CompanyUsernameExistException | CustomerUsernameExistException | CreateNewSaleTransactionException | CustomerNotFoundException ex) {
            ex.printStackTrace();
        }

    }
}
