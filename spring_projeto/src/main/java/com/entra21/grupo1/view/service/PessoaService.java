package com.entra21.grupo1.view.service;

import com.entra21.grupo1.model.dto.*;
import com.entra21.grupo1.model.entity.IngressoEntity;
import com.entra21.grupo1.model.entity.PessoaEntity;
import com.entra21.grupo1.view.repository.CadeiraRepository;
import com.entra21.grupo1.view.repository.IngressoRepository;
import com.entra21.grupo1.view.repository.PessoaRepository;
import com.entra21.grupo1.view.repository.SessaoRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoaService implements UserDetailsService {
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private IngressoRepository ingressoRepository;
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private CadeiraRepository cadeiraRepository;


    /**Busca todos os usuários do banco de dados.
     * @return List<PessoaDTO> - Retorna uma lista de DTO de todas as pessoas existentes.
     */
    public List<PessoaDTO> getAll() {
        return pessoaRepository.findAll().stream().map( pessoa -> {
            PessoaDTO dto = new PessoaDTO();
            dto.setId(pessoa.getId());
            dto.setNome(pessoa.getNome());
            dto.setSobrenome(pessoa.getSobrenome());
            dto.setTelefone(pessoa.getTelefone());
            dto.setCpf(pessoa.getCpf());
            dto.setSaldoCarteira(pessoa.getSaldoCarteira());
            dto.setLogin(pessoa.getLogin());
            dto.setSenha(pessoa.getSenha());
            dto.setCinemas(pessoa.getCinemas().stream().map( cinemaEntity -> {
                CinemaDTO cinemaDTO = new CinemaDTO();
                cinemaDTO.setId(cinemaEntity.getId());
                cinemaDTO.setNome(cinemaEntity.getNome());
                cinemaDTO.setCaixa(cinemaEntity.getCaixa());
                return cinemaDTO;
            }).collect(Collectors.toList()));
            return dto;
        }).collect(Collectors.toList());
    }

    /**Busca todos os ingressos que o usuário em questão possui.
     * @param id Long - Identificador do usuário.
     * @return List<MeusIngressosDTO> - Retorna uma lista de DTO de todos os ingressos do usuário.
     */
    public List<MeusIngressosDTO> meusIngressos(@NotNull Long id) {
        return ingressoRepository.findMeuIngressos(id).stream().map( ingresso -> {
            MeusIngressosDTO x = new MeusIngressosDTO();
            x.setId(ingresso.getId());
            x.setDataCompra(ingresso.getDataCompra());
            x.setCodigo(ingresso.getCadeira().getCodigo());
            x.setTipoCadeira(ingresso.getCadeira().getTipoCadeira());
            x.setFileira(ingresso.getCadeira().getFileira());
            x.setOrdemFileira(ingresso.getCadeira().getOrdemFileira());
            x.setNomeSala(ingresso.getCadeira().getSala().getNome());
            x.setNomeCinema(ingresso.getCadeira().getSala().getCinema().getNome());
            x.setDataSessao(ingresso.getSessao().getDataSessao());
            x.setValorInteira(ingresso.getSessao().getValorInteira());
            x.setValorMeia(ingresso.getSessao().getValorMeia());
            x.setTipoSessao(ingresso.getSessao().getTipoSessao());
            x.setNomeFilme(ingresso.getSessao().getFilme().getNome());
            return x;
        }).collect(Collectors.toList());
    }

    /**Adiciona um novo usuário ao banco de dados.
     * @param input PessoaPayloadDTO - Dados de um novo usuário
     * @return PessoaDTO - Dados salvos do usuário
     */
    public PessoaDTO save(@NotNull PessoaPayloadDTO input) {
        PessoaEntity newEntity = new PessoaEntity();
        newEntity.setNome(input.getNome());
        newEntity.setSobrenome(input.getSobrenome());
        newEntity.setTelefone(input.getTelefone());
        newEntity.setCpf(input.getCpf());
        newEntity.setSaldoCarteira(input.getSaldoCarteira());
        newEntity.setLogin(input.getLogin());
        newEntity.setSenha(input.getSenha());
        pessoaRepository.save(newEntity);
        return pessoaRepository.findByLogin(newEntity.getLogin()).toDTO();
    }

    /**Atualiza informações dos usuários no banco de dados.
     * @param newPessoa PessoaDTO - Dados de um usuário que será atualizado.
     * @return PessoaDTO - Dados atualizados do cinema.
     */
    public PessoaDTO update(@NotNull PessoaDTO newPessoa) {
        PessoaEntity e = pessoaRepository.findById(newPessoa.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pessoa não encontrada!"));

        if(newPessoa.getNome() != null) e.setNome(newPessoa.getNome());
        if(newPessoa.getSobrenome() != null) e.setSobrenome(newPessoa.getSobrenome());
        if(newPessoa.getTelefone() != null) e.setTelefone(newPessoa.getTelefone());
        if(newPessoa.getCpf() != null) e.setCpf(newPessoa.getCpf());
        if(newPessoa.getNome() != null) e.setNome(newPessoa.getNome());
        if(newPessoa.getSaldoCarteira() != null) e.setSaldoCarteira(newPessoa.getSaldoCarteira());
        if(newPessoa.getSenha() != null) e.setSenha(newPessoa.getSenha());
        pessoaRepository.save(e);

        return e.toDTO();
    }

    /**Deleta informações do usuário do banco de dados.
     * @param id Long - Identificador de um cinema existente
     */
    public void delete(@NotNull Long id) {pessoaRepository.deleteById(id);}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PessoaEntity user = pessoaRepository.findByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }
}
