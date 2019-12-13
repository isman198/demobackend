package id.co.tech.cakra.pegadaian.demo.web.rest;

import id.co.tech.cakra.pegadaian.demo.DemobackendApp;

import id.co.tech.cakra.pegadaian.demo.domain.HargaPenutupan;
import id.co.tech.cakra.pegadaian.demo.repository.HargaPenutupanRepository;
import id.co.tech.cakra.pegadaian.demo.service.HargaPenutupanService;
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
 * Test class for the HargaPenutupanResource REST controller.
 *
 * @see HargaPenutupanResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemobackendApp.class)
public class HargaPenutupanResourceIntTest {

    private static final LocalDate DEFAULT_TANGGAL = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TANGGAL = LocalDate.now(ZoneId.systemDefault());

    private static final Double DEFAULT_HARGA = 0D;
    private static final Double UPDATED_HARGA = 1D;

    @Autowired
    private HargaPenutupanRepository hargaPenutupanRepository;

    @Autowired
    private HargaPenutupanService hargaPenutupanService;

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

    private MockMvc restHargaPenutupanMockMvc;

    private HargaPenutupan hargaPenutupan;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HargaPenutupanResource hargaPenutupanResource = new HargaPenutupanResource(hargaPenutupanService);
        this.restHargaPenutupanMockMvc = MockMvcBuilders.standaloneSetup(hargaPenutupanResource)
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
    public static HargaPenutupan createEntity(EntityManager em) {
        HargaPenutupan hargaPenutupan = new HargaPenutupan()
            .tanggal(DEFAULT_TANGGAL)
            .harga(DEFAULT_HARGA);
        return hargaPenutupan;
    }

    @Before
    public void initTest() {
        hargaPenutupan = createEntity(em);
    }

    @Test
    @Transactional
    public void createHargaPenutupan() throws Exception {
        int databaseSizeBeforeCreate = hargaPenutupanRepository.findAll().size();

        // Create the HargaPenutupan
        restHargaPenutupanMockMvc.perform(post("/api/harga-penutupans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hargaPenutupan)))
            .andExpect(status().isCreated());

        // Validate the HargaPenutupan in the database
        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeCreate + 1);
        HargaPenutupan testHargaPenutupan = hargaPenutupanList.get(hargaPenutupanList.size() - 1);
        assertThat(testHargaPenutupan.getTanggal()).isEqualTo(DEFAULT_TANGGAL);
        assertThat(testHargaPenutupan.getHarga()).isEqualTo(DEFAULT_HARGA);
    }

    @Test
    @Transactional
    public void createHargaPenutupanWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hargaPenutupanRepository.findAll().size();

        // Create the HargaPenutupan with an existing ID
        hargaPenutupan.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHargaPenutupanMockMvc.perform(post("/api/harga-penutupans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hargaPenutupan)))
            .andExpect(status().isBadRequest());

        // Validate the HargaPenutupan in the database
        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTanggalIsRequired() throws Exception {
        int databaseSizeBeforeTest = hargaPenutupanRepository.findAll().size();
        // set the field null
        hargaPenutupan.setTanggal(null);

        // Create the HargaPenutupan, which fails.

        restHargaPenutupanMockMvc.perform(post("/api/harga-penutupans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hargaPenutupan)))
            .andExpect(status().isBadRequest());

        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllHargaPenutupans() throws Exception {
        // Initialize the database
        hargaPenutupanRepository.saveAndFlush(hargaPenutupan);

        // Get all the hargaPenutupanList
        restHargaPenutupanMockMvc.perform(get("/api/harga-penutupans?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hargaPenutupan.getId().intValue())))
            .andExpect(jsonPath("$.[*].tanggal").value(hasItem(DEFAULT_TANGGAL.toString())))
            .andExpect(jsonPath("$.[*].harga").value(hasItem(DEFAULT_HARGA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getHargaPenutupan() throws Exception {
        // Initialize the database
        hargaPenutupanRepository.saveAndFlush(hargaPenutupan);

        // Get the hargaPenutupan
        restHargaPenutupanMockMvc.perform(get("/api/harga-penutupans/{id}", hargaPenutupan.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hargaPenutupan.getId().intValue()))
            .andExpect(jsonPath("$.tanggal").value(DEFAULT_TANGGAL.toString()))
            .andExpect(jsonPath("$.harga").value(DEFAULT_HARGA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingHargaPenutupan() throws Exception {
        // Get the hargaPenutupan
        restHargaPenutupanMockMvc.perform(get("/api/harga-penutupans/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHargaPenutupan() throws Exception {
        // Initialize the database
        hargaPenutupanService.save(hargaPenutupan);

        int databaseSizeBeforeUpdate = hargaPenutupanRepository.findAll().size();

        // Update the hargaPenutupan
        HargaPenutupan updatedHargaPenutupan = hargaPenutupanRepository.findById(hargaPenutupan.getId()).get();
        // Disconnect from session so that the updates on updatedHargaPenutupan are not directly saved in db
        em.detach(updatedHargaPenutupan);
        updatedHargaPenutupan
            .tanggal(UPDATED_TANGGAL)
            .harga(UPDATED_HARGA);

        restHargaPenutupanMockMvc.perform(put("/api/harga-penutupans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHargaPenutupan)))
            .andExpect(status().isOk());

        // Validate the HargaPenutupan in the database
        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeUpdate);
        HargaPenutupan testHargaPenutupan = hargaPenutupanList.get(hargaPenutupanList.size() - 1);
        assertThat(testHargaPenutupan.getTanggal()).isEqualTo(UPDATED_TANGGAL);
        assertThat(testHargaPenutupan.getHarga()).isEqualTo(UPDATED_HARGA);
    }

    @Test
    @Transactional
    public void updateNonExistingHargaPenutupan() throws Exception {
        int databaseSizeBeforeUpdate = hargaPenutupanRepository.findAll().size();

        // Create the HargaPenutupan

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHargaPenutupanMockMvc.perform(put("/api/harga-penutupans")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hargaPenutupan)))
            .andExpect(status().isBadRequest());

        // Validate the HargaPenutupan in the database
        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHargaPenutupan() throws Exception {
        // Initialize the database
        hargaPenutupanService.save(hargaPenutupan);

        int databaseSizeBeforeDelete = hargaPenutupanRepository.findAll().size();

        // Delete the hargaPenutupan
        restHargaPenutupanMockMvc.perform(delete("/api/harga-penutupans/{id}", hargaPenutupan.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<HargaPenutupan> hargaPenutupanList = hargaPenutupanRepository.findAll();
        assertThat(hargaPenutupanList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HargaPenutupan.class);
        HargaPenutupan hargaPenutupan1 = new HargaPenutupan();
        hargaPenutupan1.setId(1L);
        HargaPenutupan hargaPenutupan2 = new HargaPenutupan();
        hargaPenutupan2.setId(hargaPenutupan1.getId());
        assertThat(hargaPenutupan1).isEqualTo(hargaPenutupan2);
        hargaPenutupan2.setId(2L);
        assertThat(hargaPenutupan1).isNotEqualTo(hargaPenutupan2);
        hargaPenutupan1.setId(null);
        assertThat(hargaPenutupan1).isNotEqualTo(hargaPenutupan2);
    }
}
