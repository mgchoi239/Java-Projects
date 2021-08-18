package byow.Core;

import java.io.Serializable;

public class Save implements Serializable {
    public Point[] dogecoin;
    public Point bitcoin;

    public Save (Point[] dogecoin, Point bitcoin){
        this.dogecoin=dogecoin;
        this.bitcoin=bitcoin;
    }

    public Point[] getDogecoin() {
        return dogecoin;
    }

    public Point getBitcoin() {
        return bitcoin;
    }
}
