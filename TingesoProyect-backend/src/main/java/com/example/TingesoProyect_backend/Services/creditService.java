package com.example.TingesoProyect_backend.Services;

import com.example.TingesoProyect_backend.Entities.User;
import com.example.TingesoProyect_backend.Entities.credit;
import com.example.TingesoProyect_backend.Repositories.CreditRepository;
import com.example.TingesoProyect_backend.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

import static com.example.TingesoProyect_backend.util.CreditStatus.APROBADA;

@Service
public class creditService {
    @Autowired
    private CreditRepository creditRepository;

    @Autowired
    private UserRepository userRepository;

    public ArrayList<credit> getAllCreditProcess(){
        return (ArrayList<credit>) creditRepository.findByProcessCredit(true);
    }

    public ArrayList<credit> getAllCredit(){
        return (ArrayList<credit>) creditRepository.findAll();
    }

    public ArrayList<credit> getCreditByRut(String rutClient){
        if (rutClient == null ){
            throw new IllegalArgumentException("por favor ingrese un rut");
        }
        if (userRepository.findByRut(rutClient) == null){
            throw new IllegalArgumentException("el usuario no esta registrado");
        }

        return (ArrayList<credit>) creditRepository.findByRutClient(rutClient);
    }

    public Optional<credit> getCreditProcessByRut(String rutClient) {
        if (rutClient == null){
            throw new IllegalArgumentException("por favor ingrese un rut");
        }
        if (userRepository.findByRut(rutClient) == null){
            throw new IllegalArgumentException("el usuario no esta registrado");
        }
        return creditRepository.findByRutClientAndProcessCredit(rutClient, true);
    }

    public credit getCreditById(long id){
        return creditRepository.findById(id);
    }

    public credit saveCredit(credit credit){

        if (credit.getRutClient() == null || credit.getRutClient().isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese el RUT");
        }

        Optional<credit> creditOptional = creditRepository.findByRutClientAndProcessCredit(credit.getRutClient(),true);
        if (creditOptional.isPresent()){
            throw new IllegalArgumentException("No puede solicitar otro prestamo ya que ya tiene uno en proceso");
        }

        if (credit.getIdloanType() == null || credit.getIdloanType() == 0){
            throw new IllegalArgumentException("por favor seleccione un tipo de credito");
        }

        if (credit.getIdloanType() == 1 || credit.getIdloanType() == 2) {
            creditOptional = creditRepository.findByRutClientAndIdloanType(credit.getRutClient(), 1);
            if (creditOptional.isPresent() && creditOptional.get().getCreditStatus() == APROBADA) {
                throw new IllegalArgumentException("No puede pedir el presente prestamo ya que solo se puede pedir una vez");
            } else {
                creditOptional = creditRepository.findByRutClientAndIdloanType(credit.getRutClient(), 2);
                if (creditOptional.isPresent() && creditOptional.get().getCreditStatus() == APROBADA) {
                    throw new IllegalArgumentException("No puede pedir el presente prestamo ya que solo se puede pedir una vez");
                }
            }
        }

        User user = userRepository.findByRut(credit.getRutClient());

        if (user == null){
            throw new IllegalArgumentException("No esta registrado este rut de usuario, por favor registrese primero antes de solicitar un prestamo");
        } else if (!user.getRegister()) {
            throw new IllegalArgumentException("Su registro aun no ha sido confirmado, por favor intente de nuevo mas tarde");
        }



        return creditRepository.save(credit);
    }

    public credit updateCredit(credit credit){

        if (credit.getRutClient() == null || credit.getRutClient().isEmpty()) {
            throw new IllegalArgumentException("Por favor ingrese el RUT");
        }

        User user = userRepository.findByRut(credit.getRutClient());

        if (user == null){
            throw new IllegalArgumentException("No esta registrado este rut de usuario, por favor registrese primero antes de solicitar un prestamo");
        } else if (!user.getRegister()) {
            throw new IllegalArgumentException("Su registro aun no ha sido confirmado, por favor intente de nuevo mas tarde");
        }

        return creditRepository.save(credit);
    }

    public boolean deleteCredit(Long id) throws Exception{
        try{
            creditRepository.deleteById(id);
            return true;
        } catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }


}
