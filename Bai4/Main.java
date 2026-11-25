import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class ConChinhSach implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class HoNgheo implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class HocBong implements MienGiam {
    private static final long MUC_GIAM = 3000000L;

    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, MUC_GIAM);
    }
}

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;

    protected final String maHe;
    protected final String maSV;
    protected final int soTinChi;
    private MienGiam mienGiam; 

    public SinhVien(String maHe, String maSV, int soTinChi) {
        this.maHe = maHe;
        this.maSV = maSV;
        this.soTinChi = soTinChi;
    }

    public String getMaHe() {
        return maHe;
    }

    public String getMaSV() {
        return maSV;
    }

    public void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    public abstract long hocPhi();

    public long soTienGiam() {
        return mienGiam == null ? 0 : mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi() + " " + soTienGiam() + " " + phaiNop();
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

    static MienGiam taoChinhSach(String ma) {
        switch (ma) {
            case "CS":
                return new ConChinhSach();
            case "HN":
                return new HoNgheo();
            case "HB":
                return new HocBong();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        List<SinhVien> ds = new ArrayList<>();
        Map<String, SinhVien> theoMa = new HashMap<>();
        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int tc = sc.nextInt();
            SinhVien sv = taoSinhVien(maHe, maSV, tc);
            ds.add(sv);
            theoMa.put(maSV, sv);
        }

        StringBuilder out = new StringBuilder();
        int m = sc.nextInt();
        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maCS = sc.next();
            SinhVien sv = theoMa.get(maSV);
            if (sv == null) {
                out.append("NOT FOUND ").append(maSV).append('\n');
            } else {
                sv.setMienGiam(taoChinhSach(maCS));
            }
        }

        for (SinhVien sv : ds) {
            out.append(sv).append('\n');
        }

        String[] heThuTu = {"CQ", "CLC", "LT"};
        for (String he : heThuTu) {
            int dem = 0;
            long tong = 0;
            for (SinhVien sv : ds) {
                if (sv.getMaHe().equals(he)) {
                    dem++;
                    tong += sv.phaiNop();
                }
            }
            if (dem > 0) {
                out.append(he).append(' ').append(dem).append(' ').append(tong).append('\n');
            }
        }
        System.out.print(out);
    }
}
