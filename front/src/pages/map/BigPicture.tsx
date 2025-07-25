import "../../assets/css/main.css";
import "../../assets/css/noscript.css";
import KakaoMap from "../../KakaoMap";

const BigPicture: React.FC = () => {
  return (
    <div className="is-preload">
      {/* Header */}
      <header id="header">
        <h1>Big Picture</h1>
        <nav>
          <ul>
            <li>
              <a href="#intro">Intro</a>
            </li>
            <li>
              <a href="#one">What I Do</a>
            </li>
            <li>
              <a href="#two">Who I Am</a>
            </li>
            <li>
              <a href="#work">My Work</a>
            </li>
            <li>
              <a href="#contact">Contact</a>
            </li>
          </ul>
        </nav>
      </header>

      {/* Intro */}
      <section id="intro" className="main style1 dark fullscreen">
        <div className="content">
          <header>
            <h2>Hey.</h2>
          </header>
          <p>
            Welcome to <strong>Big Picture</strong>, a responsive site template
            designed by <a href="https://html5up.net">HTML5 UP</a>
            <br />
            and released for free under the{" "}
            <a href="https://html5up.net/license">
              Creative Commons Attribution license
            </a>
            .
          </p>
          <footer>
            <a href="#one" className="button style2 down">
              More
            </a>
          </footer>
        </div>
      </section>

      {/* One */}
      <section id="one" className="main style2 right dark fullscreen">
        <div className="content box style2">
          <header>
            <h2>What I Do</h2>
          </header>
          <p>
            Lorem ipsum dolor sit amet et sapien sed elementum egestas dolore
            condimentum. Fusce blandit ultrices sapien, in accumsan orci rhoncus
            eu. Sed sodales venenatis arcu, id varius justo euismod in.
            Curabitur egestas consectetur magna.
          </p>
        </div>
        <a href="#two" className="button style2 down anchored">
          Next
        </a>
      </section>

      {/* Two */}
      <section id="two" className="main style2 left dark fullscreen">
        <div className="content box style2">
          <header>
            <h2>Who I Am</h2>
          </header>
          <p>
            Lorem ipsum dolor sit amet et sapien sed elementum egestas dolore
            condimentum. Fusce blandit ultrices sapien, in accumsan orci rhoncus
            eu. Sed sodales venenatis arcu, id varius justo euismod in.
            Curabitur egestas consectetur magna.
          </p>
        </div>
        <a href="#work" className="button style2 down anchored">
          Next
        </a>
      </section>

      <section id="map-section" className="main style3 secondary">
        <div className="content">
          <header>
            <h2>Map</h2>
            <p>Here is our location</p>
          </header>
          <KakaoMap />
        </div>
      </section>

      {/* Work */}
      <section id="work" className="main style3 primary">
        <div className="content">
          <header>
            <h2>My Work</h2>
            <p>
              Lorem ipsum dolor sit amet et sapien sed elementum egestas dolore
              condimentum. Fusce blandit ultrices sapien, in accumsan orci
              rhoncus eu. Sed sodales venenatis arcu, id varius justo euismod
              in. Curabitur egestas consectetur magna vitae.
            </p>
          </header>

          {/* Gallery */}
          <div className="gallery">
            {[1, 2, 3, 4, 5, 6].map((num, i) => (
              <article
                className={i % 2 === 0 ? "from-left" : "from-right"}
                key={num}
              >
                <a href={`./images/fulls/0${num}.jpg`} className="image fit">
                  <img
                    src={`./images/thumbs/0${num}.jpg`}
                    title={`Image ${num}`}
                    alt=""
                  />
                </a>
              </article>
            ))}
          </div>
        </div>
      </section>

      {/* Contact */}
      <section id="contact" className="main style3 secondary">
        <div className="content">
          <header>
            <h2>Say Hello.</h2>
            <p>
              Lorem ipsum dolor sit amet et sapien sed elementum egestas dolore
              condimentum.
            </p>
          </header>
          <div className="box">
            <form method="post" action="#">
              <div className="fields">
                <div className="field half">
                  <input type="text" name="name" placeholder="Name" />
                </div>
                <div className="field half">
                  <input type="email" name="email" placeholder="Email" />
                </div>
                <div className="field">
                  <textarea
                    name="message"
                    placeholder="Message"
                    rows={6}
                  ></textarea>
                </div>
              </div>
              <ul className="actions special">
                <li>
                  <input type="submit" value="Send Message" />
                </li>
              </ul>
            </form>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer id="footer">
        <ul className="icons">
          <li>
            <a href="#" className="icon brands fa-twitter">
              <span className="label">Twitter</span>
            </a>
          </li>
          <li>
            <a href="#" className="icon brands fa-facebook-f">
              <span className="label">Facebook</span>
            </a>
          </li>
          <li>
            <a href="#" className="icon brands fa-instagram">
              <span className="label">Instagram</span>
            </a>
          </li>
          <li>
            <a href="#" className="icon brands fa-linkedin-in">
              <span className="label">LinkedIn</span>
            </a>
          </li>
          <li>
            <a href="#" className="icon brands fa-dribbble">
              <span className="label">Dribbble</span>
            </a>
          </li>
          <li>
            <a href="#" className="icon brands fa-pinterest">
              <span className="label">Pinterest</span>
            </a>
          </li>
        </ul>
        <ul className="menu">
          <li>&copy; Untitled</li>
          <li>
            Design: <a href="https://html5up.net">HTML5 UP</a>
          </li>
        </ul>
      </footer>
    </div>
  );
};

export default BigPicture;
