package transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Created by daojia on 2018-4-19.
 */
@Service
public class service {
    @Autowired
    private dao dao;
    @Autowired
    private TransactionTemplate template;

    public void TemplateAdd(String name,int age,double money){
        dao.TemplateAdd(name,age,money);
    }

    public void reduceMoney(String name,double money){
        dao.outMoney(name,money);
    }

    public void addMoney(String name,double money){
        dao.inMoney(name,money);
    }

    public void transfer(String out,String in,double money){
        dao.outMoney(out,money);
        dao.inMoney(in,money);
    }

    public void transactionTransfer(final String out,final String in,final double money){
        template.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                dao.outMoney(out,money);
                dao.inMoney(in,money);
            }
        });
    }
}
