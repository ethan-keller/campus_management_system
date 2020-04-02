package nl.tudelft.oopp.demo.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DbConfigTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;

    /**
     * Test for dataSource method.
     */
    @Test
    public void dataSourceTest() throws Exception {
        when(dataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockConnection.createStatement().executeUpdate(any())).thenReturn(1);

        verify(mockConnection, times(1)).createStatement();
    }
}
