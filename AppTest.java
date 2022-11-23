package sber.example;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
/**
 * Unit test for simple App.
 */


public class AppTest
{
    @Test
    public void checkTicketCorrectNumLessTwo () {
        Assertions.assertEquals(true,LuckyTicket.validateInputTicketNumber(1),"NotOk");
    }

    @Test
    public void checkTicketCorrectNumGreatherSix () {
        Assertions.assertEquals(true,LuckyTicket.validateInputTicketNumber(1112341),"NotOk");
    }

    @Test
    public void checkCorrectTicketNum () {
        Assertions.assertEquals(true,LuckyTicket.validateInputTicketNumber(123321),"NotOk");
    }

}

