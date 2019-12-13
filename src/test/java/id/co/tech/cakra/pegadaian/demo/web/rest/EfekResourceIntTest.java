package id.co.tech.cakra.pegadaian.demo.web.rest;

import id.co.tech.cakra.pegadaian.demo.DemobackendApp;

import id.co.tech.cakra.pegadaian.demo.domain.Efek;
import id.co.tech.cakra.pegadaian.demo.repository.EfekRepository;
import id.co.tech.cakra.pegadaian.demo.service.EfekService;
import id.co.tech.cakra.pegadaian.demo.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static id.co.tech.cakra.pegadaian.demo.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EfekResource REST controller.
 *
 * @see EfekResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemobackendApp.class)
public class EfekResourceIntTest {

    private static final String DEFAULT_KODE_EFEK = "AAAAAAAAAA";
    private static final String UPDATED_KODE_EFEK = "BBBBBBBBBB";

    private static final String DEFAULT_NAMA_EFEK = "AAAAAAAAAA";
    private static final String UPDATED_NAMA_EFEK = "BBBBBBBBBB";

    private static final Double DEFAULT_CLOSING_PRICE = 0D;
    private static final Double UPDATED_CLOSING_PRICE = 1D;

    private static final LocalDate DEFAULT_CLOSING_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CLOSING_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_STATUS_GADAI = false;
    private static final Boolean UPDATED_STATUS_GADAI = true;

    @Autowired
    private EfekRepository efekRepository;

    @Autowired
    private EfekService efekService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restEfekMockMvc;

    private Efek efek;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EfekResource efekResource = new EfekResource(efekService);
        this.restEfekMockMvc = MockMvcBuilders.standaloneSetup(efekResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Efek createEntity(EntityManager em) {
        Efek efek = new Efek()
            .kodeEfek(DEFAULT_KODE_EFEK)
            .namaEfek(DEFAULT_NAMA_EFEK)
            .closingPrice(DEFAULT_CLOSING_PRICE)
            .closingDate(DEFAULT_CLOSING_DATE)
            .statusGadai(DEFAULT_STATUS_GADAI);
        return efek;
    }

    @Before
    public void initTest() {
        efek = createEntity(em);
    }

    @Test
    @Transactional
    public void createEfek() throws Exception {
        int databaseSizeBeforeCreate = efekRepository.findAll().size();

        // Create the Efek
        restEfekMockMvc.perform(post("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efek)))
            .andExpect(status().isCreated());

        // Validate the Efek in the database
        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeCreate + 1);
        Efek testEfek = efekList.get(efekList.size() - 1);
        assertThat(testEfek.getKodeEfek()).isEqualTo(DEFAULT_KODE_EFEK);
        assertThat(testEfek.getNamaEfek()).isEqualTo(DEFAULT_NAMA_EFEK);
        assertThat(testEfek.getClosingPrice()).isEqualTo(DEFAULT_CLOSING_PRICE);
        assertThat(testEfek.getClosingDate()).isEqualTo(DEFAULT_CLOSING_DATE);
        assertThat(testEfek.isStatusGadai()).isEqualTo(DEFAULT_STATUS_GADAI);
    }

    @Test
    @Transactional
    public void createEfekWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = efekRepository.findAll().size();

        // Create the Efek with an existing ID
        efek.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEfekMockMvc.perform(post("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efek)))
            .andExpect(status().isBadRequest());

        // Validate the Efek in the database
        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkKodeEfekIsRequired() throws Exception {
        int databaseSizeBeforeTest = efekRepository.findAll().size();
        // set the field null
        efek.setKodeEfek(null);

        // Create the Efek, which fails.

        restEfekMockMvc.perform(post("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efek)))
            .andExpect(status().isBadRequest());

        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNamaEfekIsRequired() throws Exception {
        int databaseSizeBeforeTest = efekRepository.findAll().size();
        // set the field null
        efek.setNamaEfek(null);

        // Create the Efek, which fails.

        restEfekMockMvc.perform(post("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efek)))
            .andExpect(status().isBadRequest());

        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEfeks() throws Exception {
        // Initialize the database
        efekRepository.saveAndFlush(efek);

        // Get all the efekList
        restEfekMockMvc.perform(get("/api/efeks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(efek.getId().intValue())))
            .andExpect(jsonPath("$.[*].kodeEfek").value(hasItem(DEFAULT_KODE_EFEK.toString())))
            .andExpect(jsonPath("$.[*].namaEfek").value(hasItem(DEFAULT_NAMA_EFEK.toString())))
            .andExpect(jsonPath("$.[*].closingPrice").value(hasItem(DEFAULT_CLOSING_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].closingDate").value(hasItem(DEFAULT_CLOSING_DATE.toString())))
            .andExpect(jsonPath("$.[*].statusGadai").value(hasItem(DEFAULT_STATUS_GADAI.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getEfek() throws Exception {
        // Initialize the database
        efekRepository.saveAndFlush(efek);

        // Get the efek
        restEfekMockMvc.perform(get("/api/efeks/{id}", efek.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(efek.getId().intValue()))
            .andExpect(jsonPath("$.kodeEfek").value(DEFAULT_KODE_EFEK.toString()))
            .andExpect(jsonPath("$.namaEfek").value(DEFAULT_NAMA_EFEK.toString()))
            .andExpect(jsonPath("$.closingPrice").value(DEFAULT_CLOSING_PRICE.doubleValue()))
            .andExpect(jsonPath("$.closingDate").value(DEFAULT_CLOSING_DATE.toString()))
            .andExpect(jsonPath("$.statusGadai").value(DEFAULT_STATUS_GADAI.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingEfek() throws Exception {
        // Get the efek
        restEfekMockMvc.perform(get("/api/efeks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEfek() throws Exception {
        // Initialize the database
        efekService.save(efek);

        int databaseSizeBeforeUpdate = efekRepository.findAll().size();

        // Update the efek
        Efek updatedEfek = efekRepository.findById(efek.getId()).get();
        // Disconnect from session so that the updates on updatedEfek are not directly saved in db
        em.detach(updatedEfek);
        updatedEfek
            .kodeEfek(UPDATED_KODE_EFEK)
            .namaEfek(UPDATED_NAMA_EFEK)
            .closingPrice(UPDATED_CLOSING_PRICE)
            .closingDate(UPDATED_CLOSING_DATE)
            .statusGadai(UPDATED_STATUS_GADAI);

        restEfekMockMvc.perform(put("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEfek)))
            .andExpect(status().isOk());

        // Validate the Efek in the database
        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeUpdate);
        Efek testEfek = efekList.get(efekList.size() - 1);
        assertThat(testEfek.getKodeEfek()).isEqualTo(UPDATED_KODE_EFEK);
        assertThat(testEfek.getNamaEfek()).isEqualTo(UPDATED_NAMA_EFEK);
        assertThat(testEfek.getClosingPrice()).isEqualTo(UPDATED_CLOSING_PRICE);
        assertThat(testEfek.getClosingDate()).isEqualTo(UPDATED_CLOSING_DATE);
        assertThat(testEfek.isStatusGadai()).isEqualTo(UPDATED_STATUS_GADAI);
    }

    @Test
    @Transactional
    public void updateNonExistingEfek() throws Exception {
        int databaseSizeBeforeUpdate = efekRepository.findAll().size();

        // Create the Efek

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEfekMockMvc.perform(put("/api/efeks")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(efek)))
            .andExpect(status().isBadRequest());

        // Validate the Efek in the database
        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEfek() throws Exception {
        // Initialize the database
        efekService.save(efek);

        int databaseSizeBeforeDelete = efekRepository.findAll().size();

        // Delete the efek
        restEfekMockMvc.perform(delete("/api/efeks/{id}", efek.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Efek> efekList = efekRepository.findAll();
        assertThat(efekList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Efek.class);
        Efek efek1 = new Efek();
        efek1.setId(1L);
        Efek efek2 = new Efek();
        efek2.setId(efek1.getId());
        assertThat(efek1).isEqualTo(efek2);
        efek2.setId(2L);
        assertThat(efek1).isNotEqualTo(efek2);
        efek1.setId(null);
        assertThat(efek1).isNotEqualTo(efek2);
    }
}
