public class Main {
    private static final Menu menu = new Menu();

    public static void main(String[] args) {
        Database database = new Database();
        menu.menuExecution();
    }
}

// make date and time right
// use of iterators
// comments
// put all the menus in one and use functions as much as possible, make sure to use the username when signing up
// Scanner scanner = new Scanner(System.in);
//        boolean isNotDone = true;
//        int tempInt = 0;
//        do {
//            try {
//                tempInt = scanner.nextInt();
//                isNotDone = false;
//            } catch (InputMismatchException e){
//                System.out.print("Wrong input try again: ");
//                scanner.nextLine();
//            }
//        } while (isNotDone);
//        System.out.print(tempInt);
// handle input exceptions in method
// handling the default mode in switch-case and getting the input again
// press enter to continue + println
// update and remove flights in admin menu