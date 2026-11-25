import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;

    protected final String maHe;
    protected final String maSV;
    protected final int soTinChi;

    public SinhVien(String maHe, String maSV, int soTinChi) {
        this.maHe = maHe;
        this.maSV = maSV;
        this.soTinChi = soTinChi;
    }

    public abstract long hocPhi();

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
    }
}

class ChinhQuy extends SinhVien {
    public ChinhQuy(String maSV, int soTinChi) {
        super("CQ", maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class ChatLuongCao extends SinhVien {
    public ChatLuongCao(String maSV, int soTinChi) {
        super("CLC", maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA * 3 / 2;
    }
}

class LienThong extends SinhVien {
    private static final long PHI_QUAN_LY = 2000000L;

    public LienThong(String maSV, int soTinChi) {
        super("LT", maSV, soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + PHI_QUAN_LY;
    }
}

public class Main {
    static SinhVien taoSinhVien(String maHe, String maSV, int soTinChi) {
        switch (maHe) {
            case "CQ":
                return new ChinhQuy(maSV, soTinChi);
            case "CLC":
                return new ChatLuongCao(maSV, soTinChi);
            default:
                return new LienThong(maSV, soTinChi);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<SinhVien> ds = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int tc = sc.nextInt();
            ds.add(taoSinhVien(maHe, maSV, tc));
        }
        StringBuilder out = new StringBuilder();
        for (SinhVien sv : ds) {
            out.append(sv).append('\n');
        }
        System.out.print(out);
    }
}
