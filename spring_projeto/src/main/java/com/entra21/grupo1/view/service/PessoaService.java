package com.entra21.grupo1.view.service;

import com.entra21.grupo1.model.dto.*;
import com.entra21.grupo1.model.entity.CinemaEntity;
import com.entra21.grupo1.model.entity.IngressoEntity;
import com.entra21.grupo1.model.entity.PessoaEntity;
import com.entra21.grupo1.view.repository.PessoaRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PessoaService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;

    private PessoaEntity user;

    /**Busca as informações do respectivo usuário salvas no banco de dados.
     * @return Dados do usuário.
     */
    public PessoaDTO getDados() {
        return getUser().toDTO();
    }

    /**Busca todos os ingressos que respectivo usuário possuí.
     * @return Lista de todos os ingressos do usuário.
     */
    public List<IngressoDTO> getIngressos() {
        return pessoaRepository.findById(getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!")).getIngressos().stream().map(IngressoEntity::toDTO).collect(Collectors.toList());
    }

    public List<CinemaDTOWithDetails> getCinemas(){
        userIsAnAdministrador();
        return pessoaRepository.findById(getUser().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!")).getCinemas().stream().map(CinemaEntity::toDTOWithDetails).collect(Collectors.toList());
    }

    /**Adiciona um novo usuário ao banco de dados.
     * @param newPessoa Dados do novo usuário.
     */
    public void savePessoa(@NotNull PessoaPayloadDTO newPessoa) {
        checkNullField(newPessoa);
        pessoaRepository.findByLogin(newPessoa.getLogin()).ifPresentOrElse(p ->  {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Este login já está em uso");
        }, () -> pessoaRepository.save(newPessoa.toEntity()));
    }

    /**Atualiza as informações do respectivo usuário no banco de dados.
     * @param newPessoa Dados do usuário que devem ser atualizados.
     */
    public void update(@NotNull PessoaPayloadDTO newPessoa) {
        if(newPessoa.getNome() != null) getUser().setNome(newPessoa.getNome());
        if(newPessoa.getSobrenome() != null) getUser().setSobrenome(newPessoa.getSobrenome());
        if(newPessoa.getTelefone() != null) getUser().setTelefone(newPessoa.getTelefone());
        if(newPessoa.getCpf() != null) getUser().setCpf(newPessoa.getCpf());
        if(newPessoa.getLogin() != null) getUser().setLogin(newPessoa.getLogin());
        if(newPessoa.getSenha() != null) getUser().setSenha(newPessoa.getSenha());
        pessoaRepository.save(getUser());
    }

    /**Adiciona valor na carteira do respectivo usuário.
     * @param valor Valor a ser depositado na carteira.
     */
    public void movimentacao(@NotNull Long valor) {
        if(!getUser().getLogin().equals("admin")) {
            getUser().setSaldoCarteira(getUser().getSaldoCarteira() + valor);
            pessoaRepository.save(getUser());
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas para usuários!");
        }
    }

    /**Deleta o respectivo usuário do banco de dados.
     */
    public void delete() {
        pessoaRepository.delete(getUser());
    }

    public void userIsAnAdministrador(){
        if (!getUser().getLogin().equals("admin")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Apenas para administradores de cinemas!");
        }
    }

    public void checkNullField(Object object)  {
        for (Field f : object.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (Objects.isNull(f.get(object))) {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Há um campo obrigatório nulo!");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void checkNullId(Object object) throws NoSuchFieldException {
        Field f = object.getClass().getDeclaredField("id");
        f.setAccessible(true);
        try {
            if (Objects.isNull(f.get(object))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O campo 'id' não pode ser nulo!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PessoaEntity getUser() {
        return (PessoaEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PessoaEntity user = pessoaRepository.findByLogin(username).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!"));
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
