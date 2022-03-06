//package org.example;
//
//import static org.junit.Assert.assertTrue;
//
//import info.schuwan.dao.PostgresTransactionsDAO;
//import info.schuwan.dao.PostgresUsersDAO;
//import info.schuwan.dao.PostgresbankaccountsDAO;
//import info.schuwan.dao.RepositoryDAO;
//import info.schuwan.models.Greeting;
//import info.schuwan.services.GreetingService;
//import org.junit.Test;
//import info.schuwan.Mockito;
//
//import java.lang.annotation.Repeatable;
//import java.sql.SQLException;
//
///**
// * Unit test for simple App.
// */
//public class AppTest
//{
//
//    private Repository<Integer, Greeting> greetingRepository = Mockito.mock(PostgresGreetingDao.class);
//    private GreetingService service = new GreetingService(greetingRepository);
//    /**
//     * Rigorous Test :-)
//     */
//    @Test
//    public void shouldAnswerWithTrue()
//    {
//        assertTrue( true );
//    }
//
//    @Test
//    public void showOffMockito() {
//        int id = 1;
//        Mockito.when(greetingRepository.getById(id)).thenReturn(new Greeting(1, "This came from mockito", null));
//        Greeting g = service.greetingById(id);
//        System.out.println(g);
//    }
//
//    @Test
//    public void showOffMockito2() {
//        int id = 2;
//        Mockito.when(greetingRepository.getById(id)).thenReturn(null);
//        Greeting g = service.greetingById(id);
//        System.out.println(g);
//    }
//
//    @Test
//    public void showOffMockito3() {
//        int id = 2;
//        Mockito.when(greetingRepository.getById(id)).thenThrow(new RuntimeException("what are you asking for"));
//        Greeting g = service.greetingById(id);
//        System.out.println(g);
//    }
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////package org.example;
////
////import static org.junit.Assert.assertTrue;
////
////import org.junit.Test;
////
/////**
//// * Unit test for simple App.
//// */
////public class AppTest
////{
////    /**
////     * Rigorous Test :-)
////     */
////    @Test
////    public void shouldAnswerWithTrue()
////    {
////        assertTrue( true );
////    }
////}
