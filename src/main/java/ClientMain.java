import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IExecutorService;
import com.hazelcast.map.IMap;
import data.Animal;

import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain {

    public static void main(String[] args) throws UnknownHostException {
        //Specyficzne dla Hazelcast
        ClientConfig clientConfig = HConfig.getClientConfig();
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        IMap<Long, Animal> animals = client.getMap( "animals" );
        AnimalHazelRepo repo = new AnimalHazelRepo(animals);
        AnimalUtils service = new AnimalUtils(repo);

        //Pętla główna programu
        System.out.println("Witaj w kliencie aplikacji ZOO\n Piotr Walczak gr. 1ID22B");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.[d]odaj zwierzę" +
                    "\n2.[u]suń zwierzę" +
                    "\n3.[a]ktualizuj zwierzę" +
                    "\n4.pobierz po [k]luczu" +
                    "\n5.pobierz [w]szystkie" +
                    "\n6.pobierz po [i]mieniu i/lub gatunku" +
                    "\n7.[o]blicz zapotrzebowanie dla zwierzat" +
                    "\n8.oblicz [c]alkowite zapotrzebowanie" +
                    "\n9.[z]akoncz");
            try {
                switch (scanner.nextLine().toLowerCase().charAt(0)) {
                    case 'd': service.addAnimal(); break;
                    case 'u': service.removeAnimal(); break;
                    case 'a': service.updateAnimal(); break;
                    case 'k': service.getByKey(); break;
                    case 'w': service.getAndPrintAll(); break;
                    case 'i': service.getByNameAndSpecies(); break;
                    case 'o': service.getFeedAmounts(); break;
                    case 'c': {
                        System.out.println("Obliczanie calkowitego zapotrzebowania dla kubełka - po stronie skladu");
                        IExecutorService executorService = client.getExecutorService("exec");
                        executorService.submitToAllMembers(new FeedTotalCalculator());
                        break;
                    }
                    case 'z': client.shutdown(); return;
                    default:
                        System.out.println("Podano nieznaną operację. Spróbuj ponownie.");
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Podano nieprawidłową operację.");
            }
        }
    }
}
