import com.hazelcast.cluster.MembershipEvent;
import com.hazelcast.cluster.MembershipListener;
import com.hazelcast.config.Config;
import com.hazelcast.core.*;
import com.hazelcast.map.IMap;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.partition.MigrationListener;
import com.hazelcast.partition.MigrationState;
import com.hazelcast.partition.PartitionService;
import com.hazelcast.partition.ReplicaMigrationEvent;
import data.Animal;

import java.net.UnknownHostException;
import java.util.Scanner;

public class MemberMain {

    public static void main(String[] args) throws UnknownHostException {
        Config config = HConfig.getConfig();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(config);

        instance.addDistributedObjectListener(new DistributedObjectListener() {

            @Override
            public void distributedObjectDestroyed(DistributedObjectEvent e) {
                System.out.println(e);
            }

            @Override
            public void distributedObjectCreated(DistributedObjectEvent e) {
                System.out.println(e);
            }
        });

        instance.getCluster().addMembershipListener(new MembershipListener() {

            @Override
            public void memberRemoved(MembershipEvent e) {
                System.out.println(e);
            }

            @Override
            public void memberAdded(MembershipEvent e) {
                System.out.println(e);
            }
        });

        PartitionService partitionService = instance.getPartitionService();
        partitionService.addMigrationListener(new MigrationListener() {

            @Override
            public void replicaMigrationFailed(ReplicaMigrationEvent e) {
                System.out.println(e);
            }

            @Override
            public void replicaMigrationCompleted(ReplicaMigrationEvent e) {
                System.out.println(e);
            }

            @Override
            public void migrationStarted(MigrationState s) {
                System.out.println(s);
            }

            @Override
            public void migrationFinished(MigrationState s) {
                System.out.println(s);
            }
        });

        IMap<Long, Animal> animals = instance.getMap("animals");
        AnimalHazelRepo repo = new AnimalHazelRepo(animals);
        AnimalUtils service = new AnimalUtils(repo);

        animals.addEntryListener((EntryAddedListener<Long, Animal>) e -> System.out.println(e), true);

        //Pętla główna programu
        System.out.println("Witaj po stronie składu aplikacji ZOO\n Piotr Walczak gr. 1ID22B");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1.[d]odaj zwierzę" +
                    "\n2.[u]suń zwierzę" +
                    "\n3.[a]ktualizuj zwierzę" +
                    "\n4.pobierz po [k]luczu" +
                    "\n5.pobierz [w]szystkie" +
                    "\n6.pobierz po [i]mieniu i/lub gatunku" +
                    "\n7.[o]blicz zapotrzebowanie dla zwierzat" +
                    "\n8.[z]akoncz");
            try {
                switch (scanner.nextLine().toLowerCase().charAt(0)) {
                    case 'd': service.addAnimal(); break;
                    case 'u': service.removeAnimal(); break;
                    case 'a': service.updateAnimal(); break;
                    case 'k': service.getByKey(); break;
                    case 'w': service.getAndPrintAll(); break;
                    case 'i': service.getByNameAndSpecies(); break;
                    case 'o': service.getFeedAmounts(); break;
                    case 'z': instance.shutdown(); return;
                    default:
                        System.out.println("Podano nieznaną operację. Spróbuj ponownie.");
                }
            } catch (StringIndexOutOfBoundsException e) {
                System.out.println("Podano nieprawidłową operację.");
            }
        }
    }
}
