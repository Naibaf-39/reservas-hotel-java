package com.reservashotel.controller;

import com.reservashotel.dao.HotelDAO;
import com.reservashotel.dao.JPAUtil;
import com.reservashotel.dao.ReservaDAO;
import com.reservashotel.dao.UsuarioDAO;
import com.reservashotel.model.Habitacion;
import com.reservashotel.model.Hotel;
import com.reservashotel.model.Reserva;
import com.reservashotel.model.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

@WebServlet("/controller")
public class ControllerServlet extends HttpServlet {
    private UsuarioDAO usuarioDAO;
    private ReservaDAO reservaDAO;
    private HotelDAO hotelDAO;

    @Override
    public void init() {
        usuarioDAO = new UsuarioDAO();
        reservaDAO = new ReservaDAO();
        hotelDAO = new HotelDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) action = "inicio";

        switch (action) {
            case "buscarHoteles":
                buscarHoteles(request, response);
                break;
            case "verDetallesHotel":
                verDetallesHotel(request, response);
                break;
            case "procesarReserva":
                procesarReserva(request, response);
                break;
            case "mostrarLogin":
                request.getRequestDispatcher("login.jsp").forward(request, response);
                break;
            case "login":
                login(request, response);
                break;
            case "mostrarRegistro":
                request.getRequestDispatcher("registro.jsp").forward(request, response);
                break;
            case "registrar":
                registrarUsuario(request, response);
                break;
            case "verMisReservas":
                verMisReservas(request, response);
                break;
            case "logout":
                logout(request, response);
                break;
            default:
                request.getRequestDispatcher("index.jsp").forward(request, response);
                break;
        }
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Usuario usuario = usuarioDAO.findByEmail(email);

        if (usuario != null && BCrypt.checkpw(password, usuario.getPassword())) {
            HttpSession session = request.getSession();
            session.setAttribute("usuarioLogueado", usuario);
            response.sendRedirect("index.jsp");
        } else {
            request.setAttribute("error", "Email o contraseña incorrectos.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    private void registrarUsuario(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (usuarioDAO.findByEmail(email) != null) {
            request.setAttribute("error", "El email ya está registrado.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
            return;
        }

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setPassword(hashedPassword);

        usuarioDAO.crear(nuevoUsuario);
        response.sendRedirect("controller?action=mostrarLogin");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        response.sendRedirect("index.jsp");
    }

    private void verMisReservas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("controller?action=mostrarLogin");
            return;
        }
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        List<Reserva> misReservas = reservaDAO.findByUsuario(usuario);
        request.setAttribute("listaReservas", misReservas);
        request.getRequestDispatcher("misReservas.jsp").forward(request, response);
    }

    private void buscarHoteles(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ciudad = request.getParameter("ciudad");
        List<Hotel> hotelesEncontrados = hotelDAO.buscarPorCiudad(ciudad);
        request.setAttribute("hoteles", hotelesEncontrados);
        request.setAttribute("ciudadBuscada", ciudad);
        request.getRequestDispatcher("resultadosBusqueda.jsp").forward(request, response);
    }

    private void verDetallesHotel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int hotelId = Integer.parseInt(request.getParameter("id"));
        Hotel hotel = hotelDAO.findById(hotelId);
        request.setAttribute("hotel", hotel);
        request.getRequestDispatcher("detallesHotel.jsp").forward(request, response);
    }

    private void procesarReserva(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            request.setAttribute("error", "Debes iniciar sesión para poder reservar.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try {
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            int habitacionId = Integer.parseInt(request.getParameter("habitacionId"));
            String fechaEntradaStr = request.getParameter("fechaEntrada");
            String fechaSalidaStr = request.getParameter("fechaSalida");

            java.util.Date fechaEntrada = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(fechaEntradaStr);
            java.util.Date fechaSalida = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(fechaSalidaStr);
            
            Reserva nuevaReserva = new Reserva();
            nuevaReserva.setUsuario(usuario);
            
            EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
            Habitacion habitacion = em.find(Habitacion.class, habitacionId);
            em.close();
            
            nuevaReserva.setHabitacion(habitacion);
            nuevaReserva.setFechaEntrada(fechaEntrada);
            nuevaReserva.setFechaSalida(fechaSalida);

            long diffInMillies = Math.abs(fechaSalida.getTime() - fechaEntrada.getTime());
            long diffInDays = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            BigDecimal costoTotal = habitacion.getPrecioPorNoche().multiply(new BigDecimal(diffInDays));
            nuevaReserva.setCostoTotal(costoTotal);

            reservaDAO.crear(nuevaReserva);

            response.sendRedirect("controller?action=verMisReservas");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("index.jsp"); 
        }
    }
}