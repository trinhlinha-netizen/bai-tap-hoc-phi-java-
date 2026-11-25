import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

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

    public String getMaSV() {
        return maSV;
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

class QuanLyHocPhi {
    private final List<SinhVien> ds = new ArrayList<>();
    private final Set<String> daCo = new HashSet<>();

    public void them(String maHe, String maSV, String tinChiStr) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException("Ma he khong hop le");
        }
        int tc;
        try {
            tc = Integer.parseInt(tinChiStr);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException("So tin chi khong phai so nguyen");
        }
        if (tc < 1 || tc > 30) {
            throw new DuLieuKhongHopLeException("So tin chi ngoai doan [1, 30]");
        }
        if (daCo.contains(maSV)) {
            throw new DuLieuKhongHopLeException("Ma sinh vien bi trung");
        }
        SinhVien sv;
        switch (maHe) {
            case "CQ":
                sv = new ChinhQuy(maSV, tc);
                break;
            case "CLC":
                sv = new ChatLuongCao(maSV, tc);
                break;
            default:
                sv = new LienThong(maSV, tc);
                break;
        }
        ds.add(sv);
        daCo.add(maSV);
    }

    public void sapXep() {
        ds.sort((a, b) -> {
            int c = Long.compare(b.hocPhi(), a.hocPhi());
            if (c != 0) {
                return c;
            }
            return a.getMaSV().compareTo(b.getMaSV());
        });
    }

    public List<SinhVien> getDanhSach() {
        return ds;
    }

    public long tongHocPhi() {
        long sum = 0;
        for (SinhVien sv : ds) {
            sum += sv.hocPhi();
        }
        return sum;
    }
}

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        QuanLyHocPhi ql = new QuanLyHocPhi();
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < n; i++) {
            String line = br.readLine();
            if (line == null) {
                break;
            }
            String[] p = line.trim().split("\\s+");
            String maHe = p.length > 0 ? p[0] : "";
            String maSV = p.length > 1 ? p[1] : "";
            String tc = p.length > 2 ? p[2] : "";
            try {
                ql.them(maHe, maSV, tc);
            } catch (DuLieuKhongHopLeException e) {
                out.append("INVALID ").append(maSV).append('\n');
            }
        }
        ql.sapXep();
        for (SinhVien sv : ql.getDanhSach()) {
            out.append(sv).append('\n');
        }
        out.append("TONG ").append(ql.tongHocPhi()).append('\n');
        System.out.print(out);
    }
}
