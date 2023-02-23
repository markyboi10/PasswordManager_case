//package Driver;
//
//import data.managers.VaultManager;
//
//import lombok.Getter;
//
//import java.io.IOException;
//
///**
// *
// * @author Mark Case
// */
//public class Main {
//
//    @Getter
//    // Vault Manager
//    public static VaultManager vaultManager;
//
//    public static void main(String[] args) {
//        /**
//         * Initialize manager
//         */
//        try {
//
//            vaultManager = new VaultManager();
//            vaultManager.init();
//
//            vaultManager.addAccountToVault("evk+aFczU8DQAyYrDYrX+w==", "bob", "thENIJTkCwZ3B5oNLomJQgLMllqGrnI=",
//                    "Peuslavh1IPQNUtMSFTcug==",
//                    "merrimack.edu");
//            vaultManager.addAccountToVault("evk+aFczU8DQAyYrDYrX+w==", "max", "thENIJTkCwZ3B5oNLomJQgLMllqGrnI=",
//                    "Peuslavh1IPQNUtMSFTcug==",
//                    "google.com");
//
//            System.out.println(vaultManager.getJSON());
//
//        } catch (IOException e) {
//            System.out.println("IOException, line 36");
//        } // End try-catch
//
//    } // End 'main' method
//
//} // End 'Main' class
