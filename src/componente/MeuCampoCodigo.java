package componente;

public class MeuCampoCodigo extends MeuCampoGenerico {

    public MeuCampoCodigo(boolean obrigatorio, String dica) {
        super(obrigatorio,dica,5);
        setEditable(false);
    }
    
    @Override
    public boolean eValido() {
       return true;
    }
}
